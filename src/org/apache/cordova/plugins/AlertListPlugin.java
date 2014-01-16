package org.apache.cordova.plugins;
/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
*/


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.AlertDialog;
import android.content.DialogInterface;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.PluginResult;
/**
 * This class provides a service.
 */
public class AlertListPlugin extends CordovaPlugin {

    static String TAG = "AlertListPlugin";

    /**
     * Constructor.
     */
    public AlertListPlugin() {
    }

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action        The action to execute.
     * @param args          JSONArry of arguments for the plugin.
     * @param callbackId    The callback id used when calling back into JavaScript.
     * @return              A PluginResult object with a status and message.
     */
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
            if (action.equals("alertlist")) {
                this.loadList(args, callbackContext);
            }
            return true;
        }

    // --------------------------------------------------------------------------
    // LOCAL METHODS
    // --------------------------------------------------------------------------
    
    public void loadList( final JSONArray thelist,  final CallbackContext callbackContext) {
    
        final CordovaInterface cordova = this.cordova;
    	
        Runnable runnable = new Runnable() {
    	
            public void run() {
    	
            	List<String> list = new ArrayList<String>();            	            			    	
		    	
				// we start with index 1 because index 0 is the title
		    	for( int x = 1; x < thelist.length(); x++) {
					try {
						list.add( thelist.getString(x) );
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	}
		    	
            	CharSequence[] items = list.toArray(new CharSequence[list.size()]);		    	
				
		    	AlertDialog.Builder builder = new AlertDialog.Builder(cordova.getActivity());
		    	try {
					builder.setTitle( thelist.getString(0) );
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // index 0 contains the title
		    	builder.setItems(items, new DialogInterface.OnClickListener() {
		    	    public void onClick(DialogInterface dialog, int item) {
		    	    	dialog.dismiss();
						// we +1 to item because item starts from 0, but from
						// thelist[0], that was the title...
                        callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, item + 1));
                        //callbackContext.sendPluginResult(pluginResult)
                        
		    	    }
		    	});
		    	AlertDialog alert = builder.create();
		    	alert.getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
		    	alert.show();    
            }
        };
        this.cordova.getActivity().runOnUiThread(runnable);
    }

}
