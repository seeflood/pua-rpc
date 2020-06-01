package toy.rpc.framework.registry.impl;

import toy.rpc.framework.common.model.URL;
import toy.rpc.framework.registry.ServiceDiscovery;
import toy.rpc.framework.registry.load.balance.LoadBalanceStrategy;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Map;

public class LocalFileServiceDiscovery implements ServiceDiscovery {
    private static final String FILE_NAME_WITH_PATH = "/tmp.txt";
    private LoadBalanceStrategy loadBalanceStrategy;

    public LocalFileServiceDiscovery(LoadBalanceStrategy loadBalanceStrategy) {
        this.loadBalanceStrategy = loadBalanceStrategy;
    }

    @Override
    public URL discover(String serviceName) {
        try {
            Map<String, List<URL>> map = loadFile();
            List<URL> urls = map.get(serviceName);
            if (urls == null || urls.size() == 0) {
                return null;
            }
            URL url = loadBalanceStrategy.choose(urls);
            return url;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, List<URL>> loadFile() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(FILE_NAME_WITH_PATH);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (Map<String, List<URL>>) objectInputStream.readObject();
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }
        }
    }
}
