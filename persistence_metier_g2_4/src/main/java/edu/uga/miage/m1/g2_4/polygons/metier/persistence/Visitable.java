package edu.uga.miage.m1.g2_4.polygons.metier.persistence;

/**
 * If you accept to be visited, implement this Interface!
 * @author <a href="mailto:christophe.saint-marcel@univ-grenoble-alpes.fr">Christophe</a>
 *
 */
public interface Visitable {
	   
	/**
	 * Accept a visitor.
	 * @param visitor 
	 */
	void accept(Visitor visitor);
}