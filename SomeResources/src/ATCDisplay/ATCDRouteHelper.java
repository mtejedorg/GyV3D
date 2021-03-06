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
// Generated from file `ATCDRouteHelper.java'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package ATCDisplay;

public final class ATCDRouteHelper
{
    public static void
    write(IceInternal.BasicStream __os, java.util.List<ATCDPosition> __v)
    {
        if(__v == null)
        {
            __os.writeSize(0);
        }
        else
        {
            __os.writeSize(__v.size());
            for(ATCDPosition __elem : __v)
            {
                __elem.__write(__os);
            }
        }
    }

    public static java.util.List<ATCDPosition>
    read(IceInternal.BasicStream __is)
    {
        java.util.List<ATCDPosition> __v;
        __v = new java.util.LinkedList<ATCDPosition>();
        final int __len0 = __is.readAndCheckSeqSize(12);
        for(int __i0 = 0; __i0 < __len0; __i0++)
        {
            ATCDPosition __elem;
            __elem = new ATCDPosition();
            __elem.__read(__is);
            __v.add(__elem);
        }
        return __v;
    }
}
