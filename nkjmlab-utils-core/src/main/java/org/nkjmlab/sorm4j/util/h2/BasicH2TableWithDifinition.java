package org.nkjmlab.sorm4j.util.h2;

import org.nkjmlab.sorm4j.Sorm;
import org.nkjmlab.sorm4j.util.table_def.BasicTableWithDefinition;
import org.nkjmlab.sorm4j.util.table_def.TableDefinition;

public class BasicH2TableWithDifinition<T> extends BasicTableWithDefinition<T>
    implements H2TableWithDefinition<T> {


  /**
   * This table instance is bind to the table name defined in the given {@link TableDefinition}.
   *
   * @param sorm
   * @param valueType
   * @param tableDifinition
   */
  public BasicH2TableWithDifinition(Sorm sorm, Class<T> valueType, TableDefinition tableDifinition) {
    super(sorm, valueType, tableDifinition);
  }

  public BasicH2TableWithDifinition(Sorm sorm, Class<T> valueType) {
    this(sorm, valueType, TableDefinition.builder(valueType).build());
  }


}
