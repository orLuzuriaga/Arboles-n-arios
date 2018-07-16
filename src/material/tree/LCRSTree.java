package material.tree;



import java.util.*;
import material.tree.iterator.TreeIteratorFactory;

/**
 * A linked class for a tree where nodes have an arbitrary number of children.
 *
 * @author Raul Cabido, Abraham Duarte, Jose Velez, Jesús Sánchez-Oro
 * @param <E> the elements stored in the tree
 */
public class LCRSTree<E> implements Tree<E> {
    
	
	 
    private class LCRSNode<T>  implements Position<T> {
          
    	
    	private T element;
    	private LCRSNode<T> leftChild,parent,rightSibling;
    	private LCRSTree<T> mytree;
    	
    	
    	
    	/*
    	 * Constructor
    	 */
    	public LCRSNode(T element,LCRSNode<T> left_child, LCRSNode<T> right_sibling,
				LCRSTree<T> mytree, LCRSNode<T> parent) {
			
			this.element = element;
			this.leftChild = left_child;
			this.rightSibling = right_sibling;
			this.mytree = mytree;
			this.parent = parent;
		}



		public T getElement() {
			return element;
		}



		public void setElement(T element) {
			this.element = element;
		}



		public LCRSNode<T> getLeftChild() {
			return leftChild;
		}



		public void setLeftChild(LCRSNode<T> leftChild) {
			this.leftChild = leftChild;
		}



		public LCRSNode<T> getParent() {
			return parent;
		}



		public void setRightSibling(LCRSNode<T> rightSibling) {
			this.rightSibling = rightSibling;
		}



		public LCRSNode<T> getRightSibling() {
			return rightSibling;
		}



		public void setParent(LCRSNode<T> parent) {
			this.parent = parent;
		}



		


		public LCRSTree<T> getMytree() {
			return mytree;
		}



		





	
    	
 



    }


    
    private LCRSNode<E> root;
   
    private TreeIteratorFactory<E> iteratorFactory;
    
    
    

    
    @Override
    public boolean isEmpty() {
    	
    	return (this.root== null);
    }
    
    

    @Override
    public boolean isInternal(Position<E> v) throws IllegalStateException {
    	
    	
    	return !isLeaf(v);
    }
    
    

    @Override
    public boolean isLeaf(Position<E> p) throws IllegalStateException {
    	LCRSNode<E> node = checkPosition(p);
         
    
    	return (node.getLeftChild() == null);	
    	
    }
    
    
    

    @Override
    public boolean isRoot(Position<E> p) throws IllegalStateException {
       LCRSNode<E> nodoComparar = checkPosition(p);
        return (nodoComparar == this.root);
    }
    
    

    @Override
    public Position<E> root() throws IllegalStateException {
    	
    	if(this.root == null) {
    		
    		throw new IllegalStateException("Tree node is empty");
    	}
    	
       return this.root;
    }

    
    
    @Override
    public Position<E> parent(Position<E> p) throws IndexOutOfBoundsException,
            IllegalStateException {

    	LCRSNode<E> node = checkPosition(p);
    	
    	if(this.isRoot(p)) {
    		
    		throw new IllegalStateException("The node not have parent, is node root");
    	}
    	
    	return (Position<E>) node.getParent();
    	
    }
    
    

    @Override
    public Iterable<? extends Position<E>> children(Position<E> p) {
    	LCRSNode<E> node = checkPosition(p);
    	LCRSNode<E> sonLeft = node.getLeftChild();
    	LCRSNode<E> hermano = null;
        List<Position<E>>children = new ArrayList<>();
        
        
        if(sonLeft != null) {

            children.add(sonLeft);
           hermano =  sonLeft.getRightSibling();
            while(hermano != null) {
            	 children.add(hermano);
            	 hermano =  hermano.getRightSibling();
            	
            	}
            
            return children;
        	
        }else{
        	
        	
        	return null;
        }
      
            
        	
        }
        
        
        
   
       	
    	
    

    
    /**
     * Modifies the element stored in a given position
     * @param p the position to be modified
     * @param e the new element to be stored
     * @return the previous element stored in the position
     * @throws IllegalStateException if the position is not valid 
     */
    public E replace(Position<E> p, E e) throws IllegalStateException {
    	
    	LCRSNode<E>node = checkPosition(p);
        E elemtSustituido = node.getElement();
        node.setElement(e);
        return elemtSustituido;
        
    	
        
    }
    
    

    @Override
    public Position<E> addRoot(E e) throws IllegalStateException {
    	
    	
    	if(!isEmpty()) {
    		throw new IllegalStateException("the tree already has root");
    		
    	}else {
    		LCRSNode<E> newNode = new LCRSNode<E>(e,null,null,this,null);
    		this.root = newNode;
    		return root;
    		
    	}
               
               
              
        
    }

    
    /**
     * Swap the elements stored in two given positions
     * @param p1 the first node to swap
     * @param p2 the second node to swap
     * @throws IllegalStateException if the position of any node is not valid
     */
    
    public void swapElements(Position<E> p1, Position<E> p2)
            throws IllegalStateException {
     
    	LCRSNode<E> node1 = checkPosition(p1);
    	LCRSNode<E> node2 = checkPosition(p2);
    	
    	if(node1 == node2 ) {
    		
    		throw new IllegalStateException("is the itself position");
    	}else {
    		
    		E temp = node1.getElement();
    		node1.setElement(node2.getElement());
    		node2.setElement(temp);
    		
    	}
    	 
    	
    			
    }

    
    
    
    private LCRSNode<E> checkPosition(Position<E> p)
            throws IllegalStateException {
        
    	if((p == null )|| ! (p instanceof LCRSNode)){
    		
    		throw new IllegalStateException("The position is invalid");
    	}
    	  LCRSNode<E> aux = (LCRSNode<E>) p;
    	  
    	  if (aux.getMytree() != this) {
              throw new IllegalStateException("The node is not from this tree");
          }
          return aux;
    }

    
    
    
    public Position<E> add(E e, Position<E> p){
        LCRSNode<E> parent = checkPosition(p);
        LCRSNode<E> newNode = new LCRSNode<E>(e,null,null,this,parent);
        LCRSNode<E> lastBrother = null;
       
         
        
        if(parent.getLeftChild() != null){
        	
        	lastBrother =checkPosition(this.lastBrother(parent));
        	
        	lastBrother.setRightSibling(newNode);
        	
        }else {
        	
        	parent.setLeftChild(newNode);
        }
       
      
        
        return newNode;
}
   

     
         
    
    
    
    
    
    
    
    private Position<E> lastBrother(Position<E> p)throws IllegalStateException{
    	 LCRSNode<E> parent = checkPosition(p);
    	 LCRSNode<E> lastBrother = parent.getLeftChild();
    	
    	  
         //Buscamos el ultimo hermano
            while(lastBrother.getRightSibling() != null  ) {
         	     
         	   lastBrother= lastBrother.getRightSibling();
         	
            }
    	
    	
    	return lastBrother;
    	
    	
    }
    
    
    

    public void remove(Position<E> p) throws IllegalStateException {
       LCRSNode<E>nodeDead = checkPosition(p);
       LCRSNode<E>preHermano;
       LCRSNode<E>parent = nodeDead.getParent();
       
       if(parent != null)
       {  
    	   /*
          Compruebo si el nodo a borrar es el primer hijo
          */
    	   if(nodeDead == parent.getLeftChild()) {
    		   parent.setLeftChild(nodeDead.getRightSibling());
    		   nodeDead.setParent(null);
    		   nodeDead.setRightSibling(null);
    		  
    	   }else {
    		   preHermano = parent.getLeftChild();
    		   while(preHermano != null){
    			 
    			   //Buscamos el nodo a borrar 
    			   if(preHermano.getRightSibling() == nodeDead) {
    				   //Junto de referencias del hermano que precede al nodo a borrar con el hermano que le sucede
    				   preHermano.setRightSibling(nodeDead.getRightSibling());
    				   nodeDead.setRightSibling(null);
    				   nodeDead.setParent(null);
    			   } 
    			   
    			   preHermano = preHermano.getRightSibling();
    		   }  
    	   }
       }else{
    	   this.root = null;
       }
       
       
    }

    
    
    public void setIterator(TreeIteratorFactory<E> iteratorFactory) {
        this.iteratorFactory = iteratorFactory;
    }

    @Override
    public Iterator<Position<E>> iterator() {
    	 return this.iteratorFactory.createIterator(this);
    }
}
