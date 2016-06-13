package com.efun.core.web.converter;

import com.efun.core.utils.DateFormatUtils;
import com.efun.core.utils.StringUtils;
import org.springframework.core.convert.ConversionException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * DateConverter
 *
 * @author Galen
 * @since 2016/6/13
 */
public class DateConverter implements ConditionalGenericConverter {

    @Override
    public boolean matches(TypeDescriptor typeDescriptor, TypeDescriptor typeDescriptor1) {
        return String.class == typeDescriptor.getType() && Date.class.isAssignableFrom(typeDescriptor1.getType());
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        Set<ConvertiblePair> types = new HashSet<ConvertiblePair>();
        types.add(new ConvertiblePair(String.class, Date.class));
        types.add(new ConvertiblePair(String.class, java.sql.Date.class));
        types.add(new ConvertiblePair(String.class, Timestamp.class));
        types.add(new ConvertiblePair(String.class, Time.class));
        return types;
    }

    @Override
    public Object convert(Object o, TypeDescriptor typeDescriptor, TypeDescriptor typeDescriptor1) {
        String sourceValue = (String) o;
        if (StringUtils.isBlank(sourceValue)) {
            return null;
        }
        try {
            Date date = DateFormatUtils.parseAll(sourceValue);

            if (Timestamp.class == typeDescriptor1.getType()) {
                return new Timestamp(date.getTime());
            } else if (java.sql.Date.class == typeDescriptor1.getType()) {
                return new java.sql.Date(date.getTime());
            } else if (Time.class == typeDescriptor1.getType()) {
                return new Time(date.getTime());
            }
            return date;
        } catch (ParseException e) {
            throw new ConversionFailedException(typeDescriptor, typeDescriptor1, o, e);
        }
    }
}
