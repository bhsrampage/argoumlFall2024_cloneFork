/* $Id$
 *****************************************************************************
 * Copyright (c) 2010 Contributors - see below
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Bob Tarling
 *****************************************************************************
 */

package org.argouml.activity2;

import org.apache.log4j.Logger;
import org.argouml.activity2.diagram.ActivityDiagramFactory;
import org.argouml.moduleloader.ModuleInterface;
import org.argouml.notation.Notation;
import org.argouml.notation.NotationName;
import org.argouml.notation.NotationProviderFactory2;
import org.argouml.notation.providers.uml.SDMessageNotationUml;
import org.argouml.persistence.PersistenceManager;
import org.argouml.uml.diagram.DiagramFactory;
import org.argouml.uml.diagram.DiagramFactoryInterface2;
import org.argouml.uml.diagram.DiagramFactory.DiagramType;
import org.argouml.uml.ui.PropPanelFactoryManager;

public class ActivityDiagramModule implements ModuleInterface {

    private static final Logger LOG = Logger
            .getLogger(ActivityDiagramModule.class);

    private ActivityDiagramPropPanelFactory propPanelFactory;
        
    public boolean enable() {
        
        propPanelFactory =
            new ActivityDiagramPropPanelFactory();
        PropPanelFactoryManager.addPropPanelFactory(propPanelFactory);
        // TODO: Remove the casting to DiagramFactoryInterface2
        // as soon as DiagramFactoryInterface is removed.
        DiagramFactory.getInstance().registerDiagramFactory(
                DiagramType.Activity, 
                (DiagramFactoryInterface2) new ActivityDiagramFactory());
        return true;
    }

    public boolean disable() {

        PropPanelFactoryManager.removePropPanelFactory(propPanelFactory);

        // TODO: Remove the casting to DiagramFactoryInterface2
        // as soon as DiagramFactoryInterface is removed.
        DiagramFactory.getInstance().registerDiagramFactory(
                DiagramType.Activity, (DiagramFactoryInterface2) null);
        return true;
    }

    public String getName() {
        return "ArgoUML-Activity";
    }

    public String getInfo(int type) {
        switch (type) {
        case DESCRIPTION:
            return "Activity diagram implementation";
        case AUTHOR:
            return "ArgoUML Team";
        case VERSION:
            return "0.28";
        case DOWNLOADSITE:
            return "http://argouml.tigris.org";
        default:
            return null;
        }
    }
}
