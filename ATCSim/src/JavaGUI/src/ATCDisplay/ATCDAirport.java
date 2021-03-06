// **********************************************************************
//
// Copyright (c) 2003-2013 ZeroC, Inc. All rights reserved.
//
// This copy of Ice is licensed to you under the terms described in the
// ICE_LICENSE file included in this distribution.
//
// **********************************************************************
//
// Ice version 3.5.1
//
// <auto-generated>
//
// Generated from file `ATCDisplay.ice'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package ATCDisplay;

public class ATCDAirport implements java.lang.Cloneable, java.io.Serializable
{
    public java.util.List<ATCDLandStrip> airportLandstrips;

    public float radious;

    public ATCDAirport()
    {
    }

    public ATCDAirport(java.util.List<ATCDLandStrip> airportLandstrips, float radious)
    {
        this.airportLandstrips = airportLandstrips;
        this.radious = radious;
    }

    public boolean
    equals(java.lang.Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        ATCDAirport _r = null;
        if(rhs instanceof ATCDAirport)
        {
            _r = (ATCDAirport)rhs;
        }

        if(_r != null)
        {
            if(airportLandstrips != _r.airportLandstrips)
            {
                if(airportLandstrips == null || _r.airportLandstrips == null || !airportLandstrips.equals(_r.airportLandstrips))
                {
                    return false;
                }
            }
            if(radious != _r.radious)
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
        int __h = 5381;
        __h = IceInternal.HashUtil.hashAdd(__h, "::ATCDisplay::ATCDAirport");
        __h = IceInternal.HashUtil.hashAdd(__h, airportLandstrips);
        __h = IceInternal.HashUtil.hashAdd(__h, radious);
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
        ATCDLandStripsHelper.write(__os, airportLandstrips);
        __os.writeFloat(radious);
    }

    public void
    __read(IceInternal.BasicStream __is)
    {
        airportLandstrips = ATCDLandStripsHelper.read(__is);
        radious = __is.readFloat();
    }

    public static final long serialVersionUID = -4640709374794892966L;
}
