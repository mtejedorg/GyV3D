// **********************************************************************
//
// Copyright (c) 2003-2011 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.4.2
//
// <auto-generated>
//
// Generated from file `ATCDLandStrip.java'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package ATCDisplay;

public class ATCDLandStrip implements java.lang.Cloneable, java.io.Serializable
{
    public ATCDPosition pos;

    public float width;

    public float length;

    public float orientation;

    public ATCDLandStrip()
    {
    }

    public ATCDLandStrip(ATCDPosition pos, float width, float length, float orientation)
    {
        this.pos = pos;
        this.width = width;
        this.length = length;
        this.orientation = orientation;
    }

    public boolean
    equals(java.lang.Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        ATCDLandStrip _r = null;
        try
        {
            _r = (ATCDLandStrip)rhs;
        }
        catch(ClassCastException ex)
        {
        }

        if(_r != null)
        {
            if(pos != _r.pos)
            {
                if(pos == null || _r.pos == null || !pos.equals(_r.pos))
                {
                    return false;
                }
            }
            if(width != _r.width)
            {
                return false;
            }
            if(length != _r.length)
            {
                return false;
            }
            if(orientation != _r.orientation)
            {
                return false;
            }

            return true;
        }

        return false;
    }

    public int
    hashCode()
    {
        int __h = 0;
        if(pos != null)
        {
            __h = 5 * __h + pos.hashCode();
        }
        __h = 5 * __h + java.lang.Float.floatToIntBits(width);
        __h = 5 * __h + java.lang.Float.floatToIntBits(length);
        __h = 5 * __h + java.lang.Float.floatToIntBits(orientation);
        return __h;
    }

    public java.lang.Object
    clone()
    {
        java.lang.Object o = null;
        try
        {
            o = super.clone();
        }
        catch(CloneNotSupportedException ex)
        {
            assert false; // impossible
        }
        return o;
    }

    public void
    __write(IceInternal.BasicStream __os)
    {
        pos.__write(__os);
        __os.writeFloat(width);
        __os.writeFloat(length);
        __os.writeFloat(orientation);
    }

    public void
    __read(IceInternal.BasicStream __is)
    {
        pos = new ATCDPosition();
        pos.__read(__is);
        width = __is.readFloat();
        length = __is.readFloat();
        orientation = __is.readFloat();
    }
}
