// $Id$
// Copyright (c) 1996-2006 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies.  This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason.  IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.


package org.argouml.uml.diagram.ui;

import java.util.Collection;

import org.argouml.kernel.Project;
import org.argouml.kernel.ProjectManager;
import org.argouml.model.Model;
import org.argouml.notation.NotationProviderFactory2;
import org.argouml.ui.targetmanager.TargetManager;
import org.argouml.uml.diagram.static_structure.ui.FigOperation;
import org.argouml.uml.notation.NotationProvider;
import org.tigris.gef.presentation.Fig;

/**
 * @author Bob Tarling
 */
public class FigOperationsCompartment extends FigFeaturesCompartment {
    /**
     * Serial version generated by Eclipse for rev 1.17
     */
    private static final long serialVersionUID = -2605582251722944961L;

    /**
     * The constructor.
     *
     * @param x x
     * @param y y
     * @param w width
     * @param h height
     */
    public FigOperationsCompartment(int x, int y, int w, int h) {
        super(x, y, w, h);
    }

    /*
     * @see org.argouml.uml.diagram.ui.FigFeaturesCompartment#getUmlCollection()
     */
    protected Collection getUmlCollection() {
        Object classifier = getGroup().getOwner();
        return Model.getFacade().getOperations(classifier);
    }

    /*
     * @see org.argouml.uml.diagram.ui.FigFeaturesCompartment#getNotationType()
     */
    protected int getNotationType() {
        return NotationProviderFactory2.TYPE_OPERATION;
    }

    /*
     * TODO: This logic should be within FigOperation
     * @see org.argouml.uml.diagram.ui.FigFeaturesCompartment#addExtraVisualisations(java.lang.Object, org.argouml.uml.diagram.ui.CompartmentFigText)
     */
    protected void addExtraVisualisations(Object umlObject, 
            CompartmentFigText comp) {
        // underline, if static (Classifier scope)
        comp.setUnderline(Model.getScopeKind().getClassifier().equals(
                Model.getFacade().getOwnerScope(umlObject)));
        // Italics if abstract
        if (Model.getFacade().isAbstract(umlObject)) {
            comp.setFont(FigNodeModelElement.getItalicLabelFont());
        } else {
            comp.setFont(FigNodeModelElement.getLabelFont());
        }
    }

    protected FigSingleLineText createFigText(
	    int x, int y, int w, int h, Fig aFig, NotationProvider np) {
        return new FigOperation(x, y, w, h, aFig, np);
    }

    /*
     * @see org.argouml.uml.diagram.ui.FigFeaturesCompartment#createFeature()
     */
    public void createFeature() {
        Object classifier = getGroup().getOwner();
        Project project = ProjectManager.getManager().getCurrentProject();

        Object model = project.getModel();
        Object voidType = project.findType("void");
        Object oper = Model.getCoreFactory().buildOperation(classifier, model,
                voidType);
        
        // TODO: Bob - Performance - we shouldn't rebuild the entire
        // compartment in fact do we have to do this at all? The Fig should be
        // listening for the change to the model and act accordingly
        populate();
        
        TargetManager.getInstance().setTarget(oper);

    }
}
