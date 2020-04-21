public class LinkedList {
    public class Node{
        private int direction;
        private int numOfCheckers;
        private Node next;

        public Node(int direction, int numOfCheckers){
            this.direction=direction;
            this.numOfCheckers=numOfCheckers;
        }

        public int getDirection() {
            return direction;
        }

        public void setDirection(int direction) {
            this.direction = direction;
        }

        public int getnumOfCheckers() {
            return numOfCheckers;
        }

        public void setnumOfCheckers(int numOfCheckers) {
            this.numOfCheckers = numOfCheckers;
        }
    }
    int size;
    Node head;
    public void add(int direction, int numOfCheckers) {
        Node curr=head;
        Node newNode=new Node(direction, numOfCheckers);
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
            curr.setnumOfCheckers(0);
            curr = curr.next;
        }
    }
    public void goTO(int direction,int counter){
        Node curr = head;
        for (int i = 0; i <=direction ; i++) {
            curr = curr.next;
        }
        curr.setnumOfCheckers(counter);
    }

}
