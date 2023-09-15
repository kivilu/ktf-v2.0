package com.kivi.framework.models;

import java.io.Serializable;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;

public abstract class BaseModel implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public String toJson() {
        return JSON.toJSONString(this, JSONWriter.Feature.FieldBased, JSONWriter.Feature.IgnoreNonFieldGetter,
            JSONWriter.Feature.NullAsDefaultValue, JSONWriter.Feature.WriteBigDecimalAsPlain,
            JSONWriter.Feature.WriteByteArrayAsBase64, JSONWriter.Feature.WriteLongAsString,
            JSONWriter.Feature.WriteNullListAsEmpty, JSONWriter.Feature.WriteNulls);
    }

}
