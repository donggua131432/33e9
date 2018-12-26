package com.e9cloud.core.support;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * 无论是MyBatis 在预处理语句中设置一个参数，还是从结果集中取出一个值时，类型处 理器被用来将获取的值以合适的方式转换成Java
 * 类型。下面这个表格描述了默认的类型处理器。
 *
 * 类型处理器 Java 类型 JDBC 类型 BooleanTypeHandler Boolean，boolean 任何兼容的布尔值
 * ByteTypeHandler Byte，byte 任何兼容的数字或字节类型 ShortTypeHandler Short，short
 * 任何兼容的数字或短整型 IntegerTypeHandler Integer，int 任何兼容的数字和整型 LongTypeHandler
 * Long，long 任何兼容的数字或长整型 FloatTypeHandler Float，float 任何兼容的数字或单精度浮点型
 * DoubleTypeHandler Double，double 任何兼容的数字或双精度浮点型 BigDecimalTypeHandler
 * BigDecimal 任何兼容的数字或十进制小数类型 StringTypeHandler String CHAR 和VARCHAR 类型
 * ClobTypeHandler String CLOB 和LONGVARCHAR 类型 NStringTypeHandler String
 * NVARCHAR 和NCHAR 类型 NClobTypeHandler String NCLOB 类型 ByteArrayTypeHandler
 * byte[] 任何兼容的字节流类型 BlobTypeHandler byte[] BLOB 和LONGVARBINARY 类型
 * DateTypeHandler Date （java.util ） TIMESTAMP 类型 DateOnlyTypeHandler Date
 * （java.util ） DATE 类型 TimeOnlyTypeHandler Date （java.util ） TIME 类型
 * SqlTimestampTypeHandler Timestamp （java.sql ） TIMESTAMP 类型 SqlDateTypeHandler
 * Date （java.sql ） DATE 类型 SqlTimeTypeHandler Time （java.sql ） TIME 类型
 * ObjectTypeHandler Any 其他或未指定类型 EnumTypeHandler Enumeration 类型
 * VARCHAR-任何兼容的字符串类型，作为代码存储（而不是索引）。
 *
 * 但是，有时候我们自定义java类型，需要入库，比如 java类型为：java.util.UUID，数据库类型为：varchar2。
 * 对java.util.UUID了解的话，java.util.UUID是没有提供属性，使用#{uuid.properties}是不可行的。
 * 这是时候我们需要使用mybatis提供typeHandle扩展来完成。
 *
 * @author wzj
 * @since 2014-11-11
 */
public class UUIDTypeHandler extends BaseTypeHandler<Object> {

    @Override
    public Object getNullableResult(ResultSet rs, String columnName)
            throws SQLException {
        return UUID.fromString(rs.getString(columnName));
    }

    @Override
    public Object getNullableResult(CallableStatement cs, int columnIndex)
            throws SQLException {
        return UUID.fromString((cs.getString(columnIndex)));
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i,
                                    Object parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, ((UUID) parameter).toString());
    }

    @Override
    public Object getNullableResult(ResultSet rs, int columnIndex)
            throws SQLException {
        return null;
    }

}