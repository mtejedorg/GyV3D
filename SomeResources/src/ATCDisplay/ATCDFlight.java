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
// Generated from file `ATCDFlight.java'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package ATCDisplay;

public class ATCDFlight implements java.lang.Cloneable, java.io.Serializable
{
    public float points;

    public float speed;

    public boolean focused;

    public String id;

    public ATCDPosition pos;

    public float inclination;

    public float bearing;

    public float collisionRadious;

    public java.util.List<ATCDPosition> flightRoute;

    public ATCDFlight()
    {
    }

    public ATCDFlight(float points, float speed, boolean focused, String id, ATCDPosition pos, float inclination, float bearing, float collisionRadious, java.util.List<ATCDPosition> flightRoute)
    {
        this.points = points;
        this.speed = speed;
        this.focused = focused;
        this.id = id;
        this.pos = pos;
        this.inclination = inclination;
        this.bearing = bearing;
        this.collisionRadious = collisionRadious;
        this.flightRoute = flightRoute;
    }

    public boolean
    equals(java.lang.Object rhs)
    {
        if(this == rhs)
        {
            return true;
        }
        ATCDFlight _r = null;
        try
        {
            _r = (ATCDFlight)rhs;
        }
        catch(ClassCastException ex)
        {
        }

        if(_r != null)
        {
            if(points != _r.points)
            {
                return false;
            }
            if(speed != _r.speed)
            {
                return false;
            }
            if(focused != _r.focused)
            {
                return false;
            }
            if(id != _r.id)
            {
                if(id == null || _r.id == null || !id.equals(_r.id))
                {
                    return false;
                }
            }
            if(pos != _r.pos)
            {
                if(pos == null || _r.pos == null || !pos.equals(_r.pos))
                {
                    return false;
                }
            }
            if(inclination != _r.inclination)
            {
                return false;
            }
            if(bearing != _r.bearing)
            {
                return false;
            }
            if(collisionRadious != _r.collisionRadious)
            {
                return false;
            }
            if(flightRoute != _r.flightRoute)
            {
                if(flightRoute == null || _r.flightRoute == null || !flightRoute.equals(_r.flightRoute))
                {
                    return false;
                }
            }

            return true;
        }

        return false;
    }

    public int
    hashCode()
    {
        int __h = 0;
        __h = 5 * __h + java.lang.Float.floatToIntBits(points);
        __h = 5 * __h + java.lang.Float.floatToIntBits(speed);
        __h = 5 * __h + (focused ? 1 : 0);
        if(id != null)
        {
            __h = 5 * __h + id.hashCode();
        }
        if(pos != null)
        {
            __h = 5 * __h + pos.hashCode();
        }
        __h = 5 * __h + java.lang.Float.floatToIntBits(inclination);
        __h = 5 * __h + java.lang.Float.floatToIntBits(bearing);
        __h = 5 * __h + java.lang.Float.floatToIntBits(collisionRadious);
        if(flightRoute != null)
        {
            __h = 5 * __h + flightRoute.hashCode();
        }
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
        __os.writeFloat(points);
        __os.writeFloat(speed);
        __os.writeBool(focused);
        __os.writeString(id);
        pos.__write(__os);
        __os.writeFloat(inclination);
        __os.writeFloat(bearing);
        __os.writeFloat(collisionRadious);
        ATCDRouteHelper.write(__os, flightRoute);
    }

    public void
    __read(IceInternal.BasicStream __is)
    {
        points = __is.readFloat();
        speed = __is.readFloat();
        focused = __is.readBool();
        id = __is.readString();
        pos = new ATCDPosition();
        pos.__read(__is);
        inclination = __is.readFloat();
        bearing = __is.readFloat();
        collisionRadious = __is.readFloat();
        flightRoute = ATCDRouteHelper.read(__is);
    }
}
