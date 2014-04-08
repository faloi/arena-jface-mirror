package org.uqbar.lacar.ui.impl.jface.builder

import java.util.concurrent.Callable

import org.eclipse.jface.databinding.swt.SWTObservables
import org.eclipse.swt.SWT
import org.eclipse.swt.events.ModifyEvent
import org.eclipse.swt.events.ModifyListener
import org.eclipse.swt.events.VerifyEvent
import org.eclipse.swt.events.VerifyListener
import org.eclipse.swt.widgets.Text
import org.uqbar.arena.widgets.TextFilter
import org.uqbar.arena.widgets.TextInputEvent
import org.uqbar.lacar.ui.impl.jface.JFaceContainer
import org.uqbar.lacar.ui.impl.jface.JFaceSkinnableControlBuilder
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder
import org.uqbar.lacar.ui.model.TextControlBuilder
import org.uqbar.arena.jface.JFaceImplicits._

class JFaceTextBuilder(container:JFaceContainer, multiLine:Boolean) 
	extends JFaceSkinnableControlBuilder[Text](container, new Text(container.getJFaceComposite(), if (multiLine) SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.WRAP else SWT.SINGLE | SWT.BORDER)) 
	with TextControlBuilder {


	override def observeValue() = new JFaceBindingBuilder(this, SWTObservables.observeText(getWidget, SWT.Modify))
	
	override def selectFinalLine() = {
	  getWidget addModifyListener((e:ModifyEvent) => getWidget setSelection(getWidget.getText.length))
	}

	override def addTextFilter(filter:TextFilter) = {
	  getWidget addVerifyListener((event:VerifyEvent) => event.doit = filter accept(event))
	}
  
}