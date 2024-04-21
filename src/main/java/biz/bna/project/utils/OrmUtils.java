package biz.bna.project.utils;

import biz.bna.project.annotations.Table;

import java.lang.annotation.Annotation;

public class OrmUtils {

    /**Метод, возвращающий класс дженерика
     * @param cls - класс, реализующий TableRepository<T>
     * @return tableName - Имя таблицы в бд
     * */
    public static String getTableName(Class cls){
        return ((Table)cls.getDeclaredAnnotations()[0]).name();
    }
}
