package org.uqbar.lacar.ui.impl.jface.builder.tables

import scala.collection.JavaConversions._
import java.util.ArrayList
import org.eclipse.jface.databinding.viewers.ViewersObservables
import org.eclipse.jface.viewers.TableLayout
import org.eclipse.jface.viewers.TableViewer
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.Composite
import org.eclipse.swt.widgets.Table
import org.uqbar.lacar.ui.impl.jface.JFaceContainer
import org.uqbar.lacar.ui.impl.jface.JFaceControlBuilder
import org.uqbar.lacar.ui.impl.jface.bindings.JFaceBindingBuilder
import org.uqbar.lacar.ui.impl.jface.tables.JFaceColumnBuilder
import org.uqbar.lacar.ui.model.LabelProvider
import org.uqbar.lacar.ui.model.TableBuilder
import org.uqbar.lacar.ui.impl.jface.tables.JFaceTableItemsBindingBuilder
import org.uqbar.lacar.ui.impl.jface.tables.JFaceTableLayoutBuilder

class JFaceTableBuilder[R](container:JFaceContainer, var itemType:Class[R]) 
	extends JFaceControlBuilder[Table](container) 
	with TableBuilder[R] {
  
  var viewer = this.createTableViewer(container.getJFaceComposite)
  initialize(viewer.getTable)
  var columns = new ArrayList[JFaceColumnBuilder[R]]

  	def createTableViewer(jFaceComposite:Composite) = {
		val viewer = new TableViewer(jFaceComposite, SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE | SWT.BORDER)
		viewer.getTable.setLinesVisible(true)
		viewer.getTable.setHeaderVisible(true)
		viewer.getTable.setLayout(new TableLayout())
		viewer
	}
  
  	override def addColumn(labelProvider:java.util.List[LabelProvider[R]]) = {
		val column = new JFaceColumnBuilder[R](this, labelProvider)
		columns.add(column)
		column
	}
	
	override def observeValue = new JFaceBindingBuilder(this, ViewersObservables.observeSingleSelection(viewer))
  	override def observeContents = new JFaceTableItemsBindingBuilder[R](this)

	override def getControlLayout = viewer getControl


	override def pack() = {
		viewer.getTable.setLayout(new JFaceTableLayoutBuilder(this).createLayout)
		columns.foreach(_ pack)
		super.pack();
	}

}