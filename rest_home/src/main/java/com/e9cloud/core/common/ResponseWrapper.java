package com.e9cloud.core.common;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.CharArrayWriter;
import java.io.PrintWriter;

/**
 * Created by dukai on 2016/11/25.
 *
 * 自定义一个响应结果包装器，将在这里提供一个基于内存的输出器来存储所有的返回给客户端的原始HTML代码
 */
public class ResponseWrapper extends HttpServletResponseWrapper {

    private PrintWriter cachedWriter;
    private CharArrayWriter bufferedWriter;

    /**
     * Constructs a response adaptor wrapping the given response.
     *
     * @param response
     * @throws IllegalArgumentException if the response is null
     */
    public ResponseWrapper(HttpServletResponse response) {
        super(response);
        //保存返回结果
        bufferedWriter = new CharArrayWriter();
        //包装PrintWriter,让所有结果通过PrintWriter写入到bufferedWriter中
        cachedWriter = new PrintWriter(bufferedWriter);
    }

    @Override
    public PrintWriter getWriter() {
        return cachedWriter;
    }

    /**
     * 获取HTML页面内容
     * @return
     */
    public String getResult(){
        return bufferedWriter.toString();
    }
}
