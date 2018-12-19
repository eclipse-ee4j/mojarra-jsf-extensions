/*
 * Copyright (c) 2005, 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package com.sun.faces.extensions.common.util;

import java.util.Iterator;
import java.util.List;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.event.PhaseId;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;

/**
 *
 * @author edburns
 */
public class Util {
    
    private Util() {
    }
    
    public static String getFormattedValue(FacesContext context, UIComponent component,
                                       Object currentValue)
        throws ConverterException {

        String result = null;
        // formatting is supported only for components that support
        // converting value attributes.
        if (!(component instanceof ValueHolder)) {
            if (currentValue != null) {
                result = currentValue.toString();
            }
            return result;
        }

        Converter converter = null;

        // If there is a converter attribute, use it to to ask application
        // instance for a converter with this identifer.
        converter = ((ValueHolder) component).getConverter();


        // if value is null and no converter attribute is specified, then
        // return a zero length String.
        if (converter == null && currentValue == null) {
            return "";
        }

        if (converter == null) {
            // Do not look for "by-type" converters for Strings
            if (currentValue instanceof String) {
                return (String) currentValue;
            }

            // if converter attribute set, try to acquire a converter
            // using its class type.

            Class converterType = currentValue.getClass();
            converter = Util.getConverterForClass(converterType, context);

            // if there is no default converter available for this identifier,
            // assume the model type to be String.
            if (converter == null) {
                result = currentValue.toString();
                return result;
            }
        }

        return converter.getAsString(context, component, currentValue);
    }    
    
    
    public static Converter getConverterForClass(Class converterClass,
                                                 FacesContext context) {
        if (converterClass == null) {
            return null;
        }
        try {            
            Application application = context.getApplication();
            return (application.createConverter(converterClass));
        } catch (Exception e) {
            return (null);
        }
    }
    
    public static PhaseId getPhaseIdFromString(String phaseIdString) {
        PhaseId result = PhaseId.ANY_PHASE;
        List<PhaseId> phaseIds = PhaseId.VALUES;
        for (PhaseId curPhase : phaseIds) {
            if (-1 != curPhase.toString().toLowerCase().
                    indexOf(phaseIdString.toLowerCase())) {
                return curPhase;
            }
        }
        return result;
    }
      
    public static RenderKit getRenderKit(FacesContext context) {
       String renderKitId = context.getViewRoot().getRenderKitId();
       renderKitId = (null != renderKitId) ? renderKitId : RenderKitFactory.HTML_BASIC_RENDER_KIT;
       return getRenderKit(context, renderKitId);
    }
 
    public static RenderKit getRenderKit(FacesContext context,
            String renderKitId) {
       RenderKitFactory fact = (RenderKitFactory)
            FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
       assert(null != fact);
 
       RenderKit curRenderKit = fact.getRenderKit(context, renderKitId);
       return curRenderKit;
    }
    
    public static boolean prefixViewTraversal(FacesContext context,
					      UIComponent root,
					      TreeTraversalCallback action) throws FacesException {
	boolean keepGoing = false;
	if (keepGoing = action.takeActionOnNode(context, root)) {
	    Iterator<UIComponent> kids = root.getFacetsAndChildren();
	    while (kids.hasNext() && keepGoing) {
		keepGoing = prefixViewTraversal(context, 
						kids.next(), 
						action);
	    }
	}
	return keepGoing;
    }

    public static interface TreeTraversalCallback {
	public boolean takeActionOnNode(FacesContext context, 
					UIComponent curNode) throws FacesException;
    }
    
}
