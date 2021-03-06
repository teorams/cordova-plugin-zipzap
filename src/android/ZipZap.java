package org.trg.zipzap;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 * This class echoes a string called from JavaScript.
 */
public class ZipZap extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {
            if (action.equals("unzip")) {
                unzip(callbackContext, args.getString(0), args.getString(1), args.getString(2));
            }

            callbackContext.success(1);
            return true;
        } catch (Exception e) {
            callbackContext.error(e.getMessage());
            return false;
        }
    }

    private void unzip(CallbackContext callbackContext, String from, String to, String password) throws Exception {
        try {

            String fromPath = removePathProtocolPrefix(from);
            String toPath = removePathProtocolPrefix(to);

            ZipFile zipFile = new ZipFile(fromPath);
            if (!password.isEmpty() && zipFile.isEncrypted()) {
                zipFile.setPassword(password.toCharArray());
            }

            zipFile.extractAll(toPath);
        } catch (ZipException e) {
            callbackContext.error(e.getMessage());
        } catch (Exception e) {
            callbackContext.error(e.getMessage());
        }
    }

    private static String removePathProtocolPrefix(String path) {

        if (path.startsWith("file://")) {
            path = path.substring(7);
        } else if (path.startsWith("file:")) {
            path = path.substring(5);
        }

        return path.replaceAll("//", "/");

    }
}
