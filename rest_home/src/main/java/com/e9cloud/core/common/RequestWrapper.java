package com.e9cloud.core.common;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 * Created by dukai on 2016/11/29.
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    private byte[] body;
    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request
     * @throws IllegalArgumentException if the request is null
     */
    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        byte[] b = new byte[1024];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        for(int i; (i = request.getInputStream().read(b)) != -1;){
            out.write(b,0,i);
        }
        this.body = out.toByteArray();
        out.close();
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        final ByteArrayInputStream in = new ByteArrayInputStream(body);
        return new ServletInputStream() {
            @Override
            public int read() throws IOException {
                return in.read();
            }
        };
    }
}
