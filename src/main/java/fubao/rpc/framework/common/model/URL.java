package fubao.rpc.framework.common.model;

import java.io.Serializable;

public class URL implements Serializable {
    private String host, port;

    public URL(String host, String port) {
        this.host = host;
        this.port = port;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
