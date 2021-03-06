package com.bytehonor.sdk.starter.jdbc;

import static org.junit.Assert.assertTrue;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

import com.bytehonor.sdk.lang.spring.function.ClassGetter;
import com.bytehonor.sdk.lang.spring.function.ObjectGetter;
import com.bytehonor.sdk.lang.spring.function.consumer.ConsumeString;
import com.bytehonor.sdk.starter.jdbc.annotation.SqlColumn;
import com.bytehonor.sdk.starter.jdbc.annotation.SqlTable;
import com.bytehonor.sdk.starter.jdbc.function.IConvert;

public class AnnotationTest {

    private static final Logger LOG = LoggerFactory.getLogger(AnnotationTest.class);

    @Test
    public void test() {
        long now = System.currentTimeMillis();
        Student student = new Student();
        student.setAge(1);
        student.setNickname("boy");
        student.setId(123L);
        student.setCreateAt(now);
        student.setUpdateAt(now);

        ObjectGetter<String> getter = student::getNickname;
        LOG.info("getter:{} = {}", getter.toString(), getter.get());

        ClassGetter<Student, ?> nn = Student::getNickname;
        Object nnApply = nn.apply(student);
        LOG.info("getNickname:{}, class:{}", nnApply, nnApply.getClass().getSimpleName());

        ClassGetter<Student, ?> ca = Student::getCreateAt;
        Object caApply = ca.apply(student);
        LOG.info("getCreateAt:{}, class:{}", caApply, caApply.getClass().getSimpleName());

        // 获取Person的Class对象
        Class<?> clazz = student.getClass();
        // 判断person对象上是否有Info注解
        if (clazz.isAnnotationPresent(SqlTable.class)) {
            LOG.info("student类上配置了SqlTable注解！");
            // 获取该对象上Info类型的注解
            SqlTable table = (SqlTable) clazz.getAnnotation(SqlTable.class);
            LOG.info("student.name:{}", table.value());

//            Annotation[] as = clazz.getAnnotations();
//            for (Annotation a : as) {
//                LOG.info("a:{}", a.toString());
//            }
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                boolean hasColumn = field.isAnnotationPresent(SqlColumn.class);
                LOG.info("field:{}, hasColumn:{}", field.getName(), hasColumn);
//                Annotation[] as = field.getAnnotations();
//                for (Annotation a : as) {
//                    LOG.info("a:{}", a.toString());
//                }
                if (hasColumn) {
                    // SqlColumn column = field.getAnnotation(SqlColumn.class); // 无别名
                    SqlColumn column = AnnotationUtils.getAnnotation(field, SqlColumn.class);
                    LOG.info("column name:{}, ignore:{}, value:{}.", column.name(), column.ignore(), column.value());
                }

            }

            assertTrue("test", true);
        }
    }

    public String getColumn(SerializedLambda lambda) {
        return lambda.getImplMethodName();
    }

    @Test
    public void test2() {
        String[] array = { "aaaa", "bbbb", "cccc" };
        List<String> list = Arrays.asList(array);
        // 使用lambda表达式
        list.forEach(x -> System.out.println(x));
        System.out.println("----------------");
        // 使用“::”方法引用
        ConsumeString action = Student::hello;
        list.forEach(action);

        IConvert<String, String> converter = Student::hi;
        String converted = converter.convert("IConvert");
        LOG.info("converted:{}", converted);
    }
}
