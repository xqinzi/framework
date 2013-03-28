package br.com.concepting.framework.persistence.helpers;

import java.io.Serializable;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

import br.com.concepting.framework.util.helpers.DateTime;

/**
 * Classe que define o mapeamento da classe DateTime no repositório de persistência.
 * 
 * @author fvilarinho
 * @since 4.0
 */
public class DateTimeType implements UserType{
    /**
     * @see org.hibernate.usertype.UserType#assemble(java.io.Serializable, java.lang.Object)
     */
    public Object assemble(Serializable cached, Object owner) throws HibernateException{
        return disassemble(cached);
    }
    
    /**
     * @see org.hibernate.usertype.UserType#deepCopy(java.lang.Object)
     */
    public Object deepCopy(Object value) throws HibernateException{
        if(value != null)
            return ((DateTime)value).clone();
        
        return null;
    }
    
    /**
     * @see org.hibernate.usertype.UserType#disassemble(java.lang.Object)
     */
    public Serializable disassemble(Object value) throws HibernateException{
        return (DateTime)deepCopy(value);
    }
    
    /**
     * @see org.hibernate.usertype.UserType#equals(java.lang.Object, java.lang.Object)
     */
    public boolean equals(Object x, Object y) throws HibernateException{
        if(x == null || y == null)
            return false;
        
        return x.equals(y);
    }
    
    /**
     * @see org.hibernate.usertype.UserType#hashCode(java.lang.Object)
     */
    public int hashCode(Object value) throws HibernateException{
        if(value != null)
            return value.hashCode();

        return 0;
    }
    
    /**
     * @see org.hibernate.usertype.UserType#isMutable()
     */
    public boolean isMutable(){
        return true;
    }
    
    /**
     * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet, java.lang.String[], java.lang.Object)
     */
    public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException{
        Object timestamp = rs.getObject(names[0]);
        
        if(timestamp != null){
            if(timestamp instanceof Date)
                return new DateTime(((Date) timestamp).getTime());
            else if(timestamp instanceof Time)
                return new DateTime(((Time) timestamp).getTime());
            else if(timestamp instanceof Timestamp)
                return new DateTime(((Timestamp) timestamp).getTime());
        }

        return null;
    }
    
    /**
     * @see org.hibernate.usertype.UserType#nullSafeSet(java.sql.PreparedStatement, java.lang.Object, int)
     */
    public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException{
        if(value == null)
            st.setNull(index, Types.TIMESTAMP);
        else
            st.setObject(index, value, Types.TIMESTAMP);
    }
    
    /**
     * @see org.hibernate.usertype.UserType#replace(java.lang.Object, java.lang.Object, java.lang.Object)
     */
    public Object replace(Object original, Object target, Object owner) throws HibernateException{
        return disassemble(original);
    }
    
    /**
     * @see org.hibernate.usertype.UserType#returnedClass()
     */
    public Class returnedClass(){
        return DateTime.class;
    }
    
    /**
     * @see org.hibernate.usertype.UserType#sqlTypes()
     */
    public int[] sqlTypes(){
        return new int[]{Types.TIMESTAMP};
    }
}
