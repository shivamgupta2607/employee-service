package com.shivam.employee.entity.converter;


import com.shivam.employee.enums.Designation;
import com.shivam.employee.enums.TeamType;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * typedef class for enum mapping to database column.
 */
public class PostgresSQLEnumType extends org.hibernate.type.EnumType {

    public void nullSafeSet(
            PreparedStatement st,
            Object value,
            int index,
            SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        if (value == null) {
            st.setNull(index, Types.OTHER);
        } else {
            if (value instanceof Designation) {

                st.setObject(
                        index,
                        ((Designation) value).name(),
                        Types.OTHER
                );
            } else if (value instanceof TeamType) {

                st.setObject(
                        index,
                        ((TeamType) value).name(),
                        Types.OTHER
                );
            }
        }
    }
}