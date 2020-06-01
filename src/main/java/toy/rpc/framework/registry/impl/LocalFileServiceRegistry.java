package toy.rpc.framework.registry.impl;

import toy.rpc.framework.common.model.URL;
import toy.rpc.framework.common.utils.LogUtils;
import toy.rpc.framework.registry.ServiceDiscovery;
import toy.rpc.framework.registry.ServiceRegistry;
import toy.rpc.framework.registry.load.balance.LoadBalanceStrategy;

import java.io.*;
import java.util.*;

public class LocalFileServiceRegistry implements ServiceRegistry {
    private static final String FILE_NAME_WITH_PATH = "/tmp.txt";

    private Map<String, List<URL>> registry = new HashMap<>();

    @Override
    public void register(Collection<String> interfaceNames, String serverAddress) {
        String[] split = serverAddress.split(":");
        URL url = new URL(split[0], split[1]);

        for (String interfaceName : interfaceNames) {
            if (!registry.containsKey(interfaceName)) {
                List<URL> list = new ArrayList<>();
                list.add(url);
                registry.put(interfaceName, list);
            } else {
                registry.get(interfaceName).add(url);
            }
            LogUtils.info(this,interfaceName+" registered.");
        }
        try {
            saveFile();
        } catch (IOException e) {
            LogUtils.error(this,"Save local file error when register services.",e);
            throw new RuntimeException(e);
        }
    }

    private void saveFile() throws IOException {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(FILE_NAME_WITH_PATH);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(registry);
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
    }

}
