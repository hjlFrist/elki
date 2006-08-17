package de.lmu.ifi.dbs.varianceanalysis;

import de.lmu.ifi.dbs.math.linearalgebra.Matrix;
import de.lmu.ifi.dbs.utilities.optionhandling.Parameterizable;

/**
 * A PCA is a principal component analysis that belongs to an object stored in a
 * database. The PCA determines the principal components of the object and
 * holds the eigenvectors and eigenvalues of the object.
 *
 * @author Elke Achtert (<a href="mailto:achtert@dbs.ifi.lmu.de">achtert@dbs.ifi.lmu.de</a>)
 */
public interface PCA extends Parameterizable {
  /**
   * Returns a copy of the matrix of eigenvectors
   * of the object to which this PCA belongs to.
   *
   * @return the matrix of eigenvectors
   */
  public Matrix getEigenvectors();

  /**
   * Returns a copy of the eigenvalues of the object to which this PCA belongs to
   * in decreasing order.
   *
   * @return the eigenvalues
   */
  public double[] getEigenvalues();

  /**
   * Returns a copy of the matrix of strong eigenvectors
   * after passing the eigen pair filter.
   *
   * @return the matrix of eigenvectors
   */
  public Matrix getStrongEigenvectors();

  /**
   * Returns a copy of the strong eigenvalues of the object
   * after passing the eigen pair filter.
   *
   * @return the eigenvalues
   */
  public double[] getStrongEigenvalues();

  /**
   * Returns a copy of the matrix of weak eigenvectors
   * after passing the eigen pair filter.
   *
   * @return the matrix of eigenvectors
   */
  public Matrix getWeakEigenvectors();

  /**
   * Returns a copy of the weak eigenvalues of the object
   * after passing the eigen pair filter.
   *
   * @return the eigenvalues
   */
  public double[] getWeakEigenvalues();
}
