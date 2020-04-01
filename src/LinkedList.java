public class LinkedList {
    private class Node{
        private int direction;
        private int cells;
        private Node next;

        public Node(int direction, int cells){
            this.direction=direction;
            this.cells=cells;
        }

        public int getDirection() {
            return direction;
        }

        public void setDirection(int direction) {
            this.direction = direction;
        }

        public int getCells() {
            return cells;
        }

        public void setCells(int cells) {
            this.cells = cells;
        }
    }
    int size;
    Node head;
    public void add(int direction, int cells) {
        Node curr=head;
        Node newNode=new Node(direction, cells);
        if(head==null){
            head=newNode;
            newNode.next=head;
        }//end if
        else{
            while(curr.next!=head){
                curr = curr.next;
            }//end while
            curr.next=newNode;
            newNode.next=head;
        }//end else
        size++;
    }//end add
    public void clear(){
        Node curr=head;
        while(curr.next!=head){
            curr.setDirection(0);
            curr.setCells(0);
            curr = curr.next;
        }
    }
    public void goTO(int direction,int counter){
        Node curr = head;
        for (int i = 0; i <=direction ; i++) {
            curr = curr.next;
        }
        curr.setCells(counter);
    }

}
