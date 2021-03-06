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
// Generated from file `ATCDLandStripsHelper.java'
//
// Warning: do not edit this file.
//
// </auto-generated>
//

package ATCDisplay;

public final class ATCDLandStripsHelper
{
    public static void
    write(IceInternal.BasicStream __os, java.util.List<ATCDLandStrip> __v)
    {
        if(__v == null)
        {
            __os.writeSize(0);
        }
        else
        {
            __os.writeSize(__v.size());
            for(ATCDLandStrip __elem : __v)
            {
                __elem.__write(__os);
            }
        }
    }

    public static java.util.List<ATCDLandStrip>
    read(IceInternal.BasicStream __is)
    {
        java.util.List<ATCDLandStrip> __v;
        __v = new java.util.LinkedList<ATCDLandStrip>();
        final int __len0 = __is.readAndCheckSeqSize(24);
        for(int __i0 = 0; __i0 < __len0; __i0++)
        {
            ATCDLandStrip __elem;
            __elem = new ATCDLandStrip();
            __elem.__read(__is);
            __v.add(__elem);
        }
        return __v;
    }
}
