package com.brainmaster.mobitronik.action;

import org.primefaces.component.accordionpanel.AccordionPanel;
import org.primefaces.event.TabChangeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class NavigationBean {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(NavigationBean.class);

	private static final String ADMINISTRATOR = "administrator";
	private static final String OPERATOR = "operator";
	private static final String PURCHASING = "purchasing";
	private static final String REPORT = "report";

	@Autowired
	private ActiveUser activeUser;

	private Integer activeIndex = 0;

	public Integer getActiveIndex() {
		return activeIndex;
	}

	public void setActiveIndex(Integer activeIndex) {
		this.activeIndex = activeIndex;
	}

	public void onTabChange(TabChangeEvent event) {
		Integer valid = 0;
		try {
			valid = Integer.valueOf(((AccordionPanel) event.getComponent()).getActiveIndex());
		} catch (Exception e) {
		}
		this.activeIndex = valid;
	}

	public boolean isAdministratorRole() {
		return activeUser.getAuthorities().contains(ADMINISTRATOR.toLowerCase())
				|| activeUser.getAuthorities().contains(ADMINISTRATOR.toUpperCase());
	}

	public boolean isOperatorRole() {
		return activeUser.getAuthorities().contains(OPERATOR.toLowerCase())
				|| activeUser.getAuthorities().contains(OPERATOR.toUpperCase());
	}

	public boolean isPurchasingRole() {
		return activeUser.getAuthorities().contains(PURCHASING.toLowerCase())
				|| activeUser.getAuthorities().contains(PURCHASING.toUpperCase());
	}

	public boolean isReportRole() {
		return activeUser.getAuthorities().contains(REPORT.toLowerCase())
				|| activeUser.getAuthorities().contains(REPORT.toUpperCase());
	}
}
