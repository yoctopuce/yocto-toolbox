/*********************************************************************
 *
 * $Id: Files.java 46698 2021-10-01 06:31:31Z web $
 *
 * Implements Files wrapper for Android toolbox
 *
 * - - - - - - - - - License information: - - - - - - - - - 
 *
 *  Copyright (C) 2011 and beyond by Yoctopuce Sarl, Switzerland.
 *
 *  Yoctopuce Sarl (hereafter Licensor) grants to you a perpetual
 *  non-exclusive license to use, modify, copy and integrate this
 *  file into your software for the sole purpose of interfacing
 *  with Yoctopuce products.
 *
 *  You may reproduce and distribute copies of this file in
 *  source or object form, as long as the sole purpose of this
 *  code is to interface with Yoctopuce products. You must retain
 *  this notice in the distributed source file.
 *
 *  You should refer to Yoctopuce General Terms and Conditions
 *  for additional information regarding your rights and
 *  obligations.
 *
 *  THE SOFTWARE AND DOCUMENTATION ARE PROVIDED 'AS IS' WITHOUT
 *  WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING 
 *  WITHOUT LIMITATION, ANY WARRANTY OF MERCHANTABILITY, FITNESS
 *  FOR A PARTICULAR PURPOSE, TITLE AND NON-INFRINGEMENT. IN NO
 *  EVENT SHALL LICENSOR BE LIABLE FOR ANY INCIDENTAL, SPECIAL,
 *  INDIRECT OR CONSEQUENTIAL DAMAGES, LOST PROFITS OR LOST DATA,
 *  COST OF PROCUREMENT OF SUBSTITUTE GOODS, TECHNOLOGY OR 
 *  SERVICES, ANY CLAIMS BY THIRD PARTIES (INCLUDING BUT NOT 
 *  LIMITED TO ANY DEFENSE THEREOF), ANY CLAIMS FOR INDEMNITY OR
 *  CONTRIBUTION, OR OTHER SIMILAR COSTS, WHETHER ASSERTED ON THE
 *  BASIS OF CONTRACT, TORT (INCLUDING NEGLIGENCE), BREACH OF
 *  WARRANTY, OR OTHERWISE.
 *
 *********************************************************************/

package com.yoctopuce.yoctopucetoolbox.functions;
import com.yoctopuce.YoctoAPI.YAPI_Exception;
import com.yoctopuce.YoctoAPI.YFileRecord;
import com.yoctopuce.YoctoAPI.YFiles;

import java.util.ArrayList;

//--- (generated code: YFiles class start)
/**
 * YFiles Class: Files function interface
 *
 * The filesystem interface makes it possible to store files
 * on some devices, for instance to design a custom web UI
 * (for networked devices) or to add fonts (on display
 * devices).
 */
 @SuppressWarnings("UnusedDeclaration")
public class Files extends Function
{
// valueCallbackFiles
    protected int _filesCount =  YFiles.FILESCOUNT_INVALID;
    protected int _freeSpace =  YFiles.FREESPACE_INVALID;
    protected YFiles _yfiles;

    public Files(YFiles yfunc)
    {
       super(yfunc);
       _yfiles = yfunc;
    }

    public Files(String hwid)
    {
       super(hwid);
       _yfiles = YFiles.FindFiles(hwid);
    }
    public void reloadBg () throws YAPI_Exception
    {
        super.reloadBg();
        _filesCount = _yfiles.get_filesCount();
        _freeSpace = _yfiles.get_freeSpace();
    }
    /**
     * Returns the number of files currently loaded in the filesystem.
     *
     * @return an integer corresponding to the number of files currently loaded in the filesystem
     *
     * On failure, throws an exception or returns Y_FILESCOUNT_INVALID.
     */
    public int getFilesCount()
    {
        return _filesCount;
    }

    /**
     * Returns the free space for uploading new files to the filesystem, in bytes.
     *
     * @return an integer corresponding to the free space for uploading new files to the filesystem, in bytes
     *
     * On failure, throws an exception or returns Y_FREESPACE_INVALID.
     */
    public int getFreeSpace()
    {
        return _freeSpace;
    }

    public static YFiles FindFiles(String func)
    {
        return YFiles.FindFiles(func);
    }

    public byte[] sendCommand(String command) throws YAPI_Exception
    {
        return _yfiles.sendCommand(command);
    }

    public int format_fs() throws YAPI_Exception
    {
        return _yfiles.format_fs();
    }

    public ArrayList<YFileRecord> get_list(String pattern) throws YAPI_Exception
    {
        return _yfiles.get_list(pattern);
    }

    public boolean fileExist(String filename) throws YAPI_Exception
    {
        return _yfiles.fileExist(filename);
    }

    public byte[] download(String pathname) throws YAPI_Exception
    {
        return _yfiles.download(pathname);
    }

    public int upload(String pathname, byte[] content) throws YAPI_Exception
    {
        return _yfiles.upload(pathname, content);
    }

    public int remove(String pathname) throws YAPI_Exception
    {
        return _yfiles.remove(pathname);
    }

//--- (end of generated code: YFiles class start)
}

