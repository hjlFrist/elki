package de.lmu.ifi.dbs.elki.utilities.optionhandling.parameterization;

import java.util.Collection;

import de.lmu.ifi.dbs.elki.utilities.optionhandling.OptionID;
import de.lmu.ifi.dbs.elki.utilities.optionhandling.ParameterException;
import de.lmu.ifi.dbs.elki.utilities.optionhandling.constraints.GlobalParameterConstraint;
import de.lmu.ifi.dbs.elki.utilities.optionhandling.parameters.Parameter;
import de.lmu.ifi.dbs.elki.utilities.pairs.Pair;

/**
 * This configuration can be "rewound" to allow the same values to be consumed
 * multiple times, by different classes. This is used in best-effort
 * parameterization when some instances might not apply given the actual data,
 * e.g. in visualization classes.
 * 
 * @author Erich Schubert
 */
public class MergedParameterization extends AbstractParameterization {
  /**
   * The parameterization we get the new values from.
   */
  private Parameterization child;

  /**
   * Parameters we used before, but have rewound
   */
  private ListParameterization current;

  /**
   * Parameters to rewind.
   */
  private java.util.Vector<Pair<OptionID, Object>> used;

  /**
   * Constructor.
   * 
   * @param child Child parameterization to wrap.
   */
  public MergedParameterization(Parameterization child) {
    super();
    this.child = child;
    this.current = new ListParameterization();
    this.used = new java.util.Vector<Pair<OptionID, Object>>();
  }

  /**
   * Rewind the configuration to the initial situation
   */
  public synchronized void rewind() {
    for(Pair<OptionID, Object> pair : used) {
      current.addParameter(pair.first, pair.second);
    }
    used.removeAllElements();
  }

  @Override
  public boolean setValueForOption(Parameter<?, ?> opt) throws ParameterException {
    if(current.setValueForOption(opt)) {
      used.add(new Pair<OptionID, Object>(opt.getOptionID(), opt.getValue()));
      return true;
    }
    if(child.setValueForOption(opt)) {
      used.add(new Pair<OptionID, Object>(opt.getOptionID(), opt.getValue()));
      return true;
    }
    return false;
  }

  @Override
  public Parameterization descend(Parameter<?, ?> option) {
    return new MergedParameterizationProxy(child.descend(option));
  }

  @Override
  public boolean hasUnusedParameters() {
    return child.hasUnusedParameters();
  }

  /**
   * Proxy class for nested parameterizations.
   * 
   * @author Erich Schubert
   */
  private class MergedParameterizationProxy implements Parameterization {
    Parameterization subchild;

    public MergedParameterizationProxy(Parameterization subchild) {
      super();
      this.subchild = subchild;
    }

    @Override
    public boolean checkConstraint(GlobalParameterConstraint constraint) {
      return MergedParameterization.this.checkConstraint(constraint);
    }

    @Override
    public Parameterization descend(Parameter<?, ?> option) {
      return new MergedParameterizationProxy(subchild.descend(option));
    }

    @Override
    public Collection<ParameterException> getErrors() {
      return MergedParameterization.this.getErrors();
    }

    @Override
    public boolean grab(Parameter<?, ?> opt) {
      return MergedParameterization.this.grab(opt);
    }

    @Override
    public boolean hasUnusedParameters() {
      return MergedParameterization.this.hasUnusedParameters();
    }

    @Override
    public void reportError(ParameterException e) {
      MergedParameterization.this.reportError(e);
    }

    @Override
    public boolean setValueForOption(Parameter<?, ?> opt) throws ParameterException {
      return MergedParameterization.this.setValueForOption(opt);
    }
  }
}