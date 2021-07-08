package com.ssh.sustain.mapper;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CodeEnumStringHandler<E extends Enum<E>> implements TypeHandler<CodeEnumString> {

    private Class<E> type;

    public CodeEnumStringHandler(Class<E> type) {
        this.type = type;
    }

    @Override
    public void setParameter(PreparedStatement ps, int i, CodeEnumString parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getCode());
    }

    @Override
    public CodeEnumString getResult(ResultSet rs, String columnName) throws SQLException {
        String code = rs.getString(columnName);
        return getCodeEnumString(code);
    }

    @Override
    public CodeEnumString getResult(ResultSet rs, int columnIndex) throws SQLException {
        String code = rs.getString(columnIndex);
        return getCodeEnumString(code);
    }

    @Override
    public CodeEnumString getResult(CallableStatement cs, int columnIndex) throws SQLException {
        String code = cs.getString(columnIndex);
        return getCodeEnumString(code);
    }

    private CodeEnumString getCodeEnumString(String code) {
        try {
            CodeEnumString[] codeEnumStrings = (CodeEnumString[]) type.getEnumConstants();
            for (CodeEnumString codeEnumString : codeEnumStrings) {
                if (codeEnumString.getCode() == code) {
                    return codeEnumString;
                }
            }
            return null;
        } catch (Exception e) {
            throw new TypeException("Can't make enum object : " + type);
        }
    }
}
