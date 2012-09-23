package com.brainmaster.mobitronik.action.error;

import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrainRetailExceptionHandler extends ExceptionHandlerWrapper {

	private static final Logger log = LoggerFactory.getLogger(BrainRetailExceptionHandler.class);
	private ExceptionHandler wrapped;

	public BrainRetailExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}

	@Override
	public void handle() throws FacesException {
		//Iterate over all unhandeled exceptions
	    Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
	    while (i.hasNext()) {
	      ExceptionQueuedEvent event = i.next();
	      ExceptionQueuedEventContext context = (ExceptionQueuedEventContext)event.getSource();
	 
	      //obtain throwable object
	      Throwable t = context.getException();
	 
	      //here you do what ever you want with exception
	      try{
	        log.error("Serious error happened!", t);
	        //redirect to error view etc....  
	      }finally{
	        //after exception is handeled, remove it from queue
	        i.remove();
	      }
	    }
	    //let the parent handle the rest
	    getWrapped().handle();
	  }
}
