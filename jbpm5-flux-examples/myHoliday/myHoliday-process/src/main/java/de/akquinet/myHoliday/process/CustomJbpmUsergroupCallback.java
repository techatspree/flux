package de.akquinet.myHoliday.process;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.jbpm.task.identity.UserGroupCallback;

import de.akquinet.jbosscc.flux.bpmservice.taskservice.qualifier.CustomFluxUserGroupCallback;
import de.akquinet.myHoliday.business.UserAccountService;
import de.akquinet.myHoliday.business.utils.UserAccountUtils;
import de.akquinet.myHoliday.domain.UserAccount;
import de.akquinet.myHoliday.domain.enums.AccountRole;

/**
 * UserGroupCallback Implementierung, die in den TaskServer injected wird und eine Verbindung zur Userdatenbank herstellt. An dieser Stelle könnte auch auf ein belibiges anderes
 * UserAndGroupSystem, wie LDAP zugegriffen werden.
 * 
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Named
@CustomFluxUserGroupCallback
public class CustomJbpmUsergroupCallback implements UserGroupCallback,
		Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	private UserAccountService userAccountServiceBean;

	@Inject
	private UserAccountUtils userAccountUtils;

	public CustomJbpmUsergroupCallback() {
	}

	@PostConstruct
	public void init() {
	}

	@Override
	public boolean existsUser(final String userId) {
		if (userAccountServiceBean.findByUserName(userId) != null) {
			return true;
		}
		return false;
	}

	/**
	 * Die Methode liefert true, wenn die übergebene Gruppe im UserAndGroupSystem existiert.
	 */
	@Override
	public boolean existsGroup(final String groupId) {
		if (groupId.equals(AccountRole.TECHNICALADMIN.getRoleId())
				|| groupId.equals(AccountRole.ADMINISTRATION.getRoleId())
				|| groupId.equals(AccountRole.USER.getRoleId())) {
			return true;
		}

		return false;
	}

	/**
	 * Die Methode liefert zu der übergebnden UserId bzw. dem UserName, alle Gruppen in die sich der User befindet.
	 */
	@Override
	public List<String> getGroupsForUser(final String userId,
			final List<String> groupIds, final List<String> allExistingGroupIds) {
		final UserAccount userAccount = userAccountServiceBean
				.findByUserName(userId);
		if (userAccount != null) {
			return userAccountUtils.userAccountRolesToStringList(userAccount);
		}
		return null;
	}
}
