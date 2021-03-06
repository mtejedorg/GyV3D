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
// Generated from file `Callback_AirportInterface_getAirportInfo.java'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package ATCDisplay;

public abstract class Callback_AirportInterface_getAirportInfo extends Ice.TwowayCallback
{
    public abstract void response(ATCDAirport __ret);

    public final void __completed(Ice.AsyncResult __result)
    {
        AirportInterfacePrx __proxy = (AirportInterfacePrx)__result.getProxy();
        ATCDAirport __ret = null;
        try
        {
            __ret = __proxy.end_getAirportInfo(__result);
        }
        catch(Ice.LocalException __ex)
        {
            exception(__ex);
            return;
        }
        response(__ret);
    }
}
