/*
 * Copyright (c) 2019, little-pan, All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library.
 */
package io.co.nio;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;

import com.offbynull.coroutines.user.Coroutine;

import io.co.CoIOException;
import io.co.CoScheduler;
import io.co.CoServerSocket;
import io.co.util.IoUtils;

/**
 * A NIO implementation of CoServerSocket.
 * 
 * @author little-pan
 * @since 2019-05-13
 *
 */
public class NioCoServerSocket extends CoServerSocket {
    
    final ServerSocketChannel channel;
    
    public NioCoServerSocket(Coroutine coAcceptor, Coroutine coConnector){
        this(coAcceptor, coConnector, new NioCoScheduler());
    }
    
    public NioCoServerSocket(Coroutine coAcceptor, Coroutine coConnector, CoScheduler coScheduler) {
        super(coAcceptor, coConnector, coScheduler);
        
        ServerSocketChannel ssChan = null;
        boolean failed = true;
        try {
            ssChan = ServerSocketChannel.open();
            ssChan.configureBlocking(false);
            this.channel = ssChan;
            failed = false;
        } catch (final IOException cause){
            throw new CoIOException(cause);
        } finally {
            if(failed){
                IoUtils.close(ssChan);
            }
        }
    }

    @Override
    public boolean isOpen() {
        return this.channel.isOpen();
    }

}
