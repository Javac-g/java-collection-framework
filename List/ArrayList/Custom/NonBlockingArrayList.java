 public class NonBlockingArrayList<T>{

        private volatile Object[] content = new Object[0];
		
        NonBlockingArrayList<T> add(int index, T item){
          if (index < 0) {
                  throw new IllegalArgumentException();                    
									  
			}
			boolean needsModification = index > content.length - 1;
			if(!needsModification){
				if(item == null){
					needsModification = content[index] != null;
				}
				else{
					needsModification = item.equals(content[index]);
				}
			}
			if(needsModification){
				final Object[] renewed = Arrays.copyOf(content, Math.max(content.length, index + 1));
				renewed[index] = item;
				content = renewed;
			}
			return this;
        }
		NonBlockingArrayList<T> remove(int index){
			if(index < 0 || index >= content.length){
				throw new IllegalArgumentException();
			}
			int size = content.length - 1;
			final Object[] renewed = new Object[size];
			System.arraycopy(content, index + 1, renewed, index, size-index);
			content = renewed;
			return this;
		}
		T get (int index){
			return (T)content[index];
		}
		int size(){
			return content.length;
		}


        NonBlockingArrayList<T> add(T item){
          return add(content.length, item);
        }

}
