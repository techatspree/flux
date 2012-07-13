package de.akquinet.myHoliday.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;

import de.akquinet.jbosscc.needle.db.transaction.TransactionHelper;
import de.akquinet.jbosscc.needle.injection.InjectionProvider;
import de.akquinet.jbosscc.needle.junit.DatabaseRule;
import de.akquinet.jbosscc.needle.junit.NeedleRule;
import de.akquinet.jbosscc.needle.mock.EasyMockProvider;
import de.akquinet.myHoliday.common.testsupport.LogInjector;

/**
 * @author <a href="mailto:pascal.schaerf@akquinet.de">Pascal Schaerf, tech@spree GmbH</a>
 * @author <a href="marek.iwaszkiewicz@akquinet.de">Marek Iwaszkiewicz, tech@spree GmbH</a>
 */
public abstract class BaseNeedleTest {

  @Rule
  public DatabaseRule databaseRule = new DatabaseRule();

  @Rule
  public NeedleRule needleRule = createNeedleRule();

  protected final EasyMockProvider provider = needleRule.getMockProvider();
  
  protected TransactionHelper transactionHelper;

  @Before
  public final void setupHelper() throws Exception {
    transactionHelper = new TransactionHelper(databaseRule.getEntityManager());
  }

  protected NeedleRule createNeedleRule() {
    final List<InjectionProvider<?>> injectionProviders = new ArrayList<InjectionProvider<?>>();
    injectionProviders.add(databaseRule);
    injectionProviders.add(new LogInjector());

    injectionProviders.addAll(getAdditionalInjectionProviders());

    return new NeedleRule(injectionProviders.toArray(new InjectionProvider[0]));
  }

  protected List<InjectionProvider<?>> getAdditionalInjectionProviders() {
    return new ArrayList<InjectionProvider<?>>();
  }

}
