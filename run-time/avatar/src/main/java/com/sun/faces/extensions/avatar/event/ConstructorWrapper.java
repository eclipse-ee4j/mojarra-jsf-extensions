/*
 * Copyright (c) 2006, 2018 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.faces.extensions.avatar.event;

import com.sun.faces.extensions.avatar.lifecycle.*;
import java.lang.reflect.Constructor;

/**
 *
 * This "struct"-like JavaClass fills a gap in the feature set of
 * <code>Constructor</code>: provide the runtime capability to query the
 * paramater list for this ctor instance.
 *
 * @author edburns
 */
class ConstructorWrapper {
    
    /** Creates a new instance of ConstructorWrapper */
    ConstructorWrapper(Constructor ctor, Class [] argClasses) {
        this.constructor = ctor;
        this.argClasses = argClasses;
    }

    /**
     * Holds value of property constructor.
     */
    private Constructor constructor;

    /**
     * Getter for property constructor.
     * @return Value of property constructor.
     */
    public Constructor getConstructor() {
        return this.constructor;
    }

    /**
     * Holds value of property argClasses.
     */
    private Class [] argClasses;

    /**
     * Getter for property argClasses.
     * @return Value of property argClasses.
     */
    public Class [] getArgClasses() {
        return this.argClasses;
    }

  
}
