package fubao.rpc.framework.registry.impl;

import fubao.rpc.framework.common.model.URL;
import fubao.rpc.framework.common.utils.LogUtils;
import fubao.rpc.framework.registry.ServiceRegistry;

import java.io.*;
import java.util.*;

public class LocalFileServiceRegistry implements ServiceRegistry {
    private static final String FILE_NAME_WITH_PATH = System.getProperty("user.dir")+ File.separator+"tmp";

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
