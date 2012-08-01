package de.akquinet.myHoliday.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

import de.akquinet.myHoliday.domain.enums.AccountRole;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
@Entity
@NamedQuery(name = UserAccountRole.QUERY_FIND_BY_ACCOUNT_ROLE, query = "Select u FROM UserAccountRole u WHERE u.accountRole = :"
		+ UserAccountRole.PARAM_ACCOUNT_ROLE)
public class UserAccountRole extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	public static final String QUERY_FIND_BY_ACCOUNT_ROLE = "findByAccountRole";
	public static final String PARAM_ACCOUNT_ROLE = "accountRole";

	@NotNull
	@Column(unique = true)
	private AccountRole accountRole;

	public UserAccountRole() {
		//
	}

	public UserAccountRole(final AccountRole accountRole) {
		this.accountRole = accountRole;
	}

	public AccountRole getAccountRole() {
		return accountRole;
	}

	public void setAccountRole(final AccountRole accountRole) {
		this.accountRole = accountRole;
	}
}
