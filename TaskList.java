package PengaturJadwal;

public class TaskList {
    private Node head;
    private int size;
    
    public TaskList() {
        head = null;
        size = 0;
    }
    
    public void tambahTask(Task task) {
        Node newNode = new Node(task);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }
    
    public void hapusTask(int index) {
        if (head == null || index >= size) {
            return;
        }
        
        if (index == 0) {
            head = head.next;
            size--;
            return;
        }
        
        Node current = head;
        Node prev = null;
        int currentIndex = 0;
        
        while (current != null && currentIndex < index) {
            prev = current;
            current = current.next;
            currentIndex++;
        }
        
        if (current != null) {
            prev.next = current.next;
            size--;
        }
    }
    
    public Task getTask(int index) {
        if (index >= size || index < 0) {
            return null;
        }
        
        Node current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }
    
    public Task[] getTaskArray() {
        Task[] tasks = new Task[size];
        Node current = head;
        int index = 0;
        
        while (current != null) {
            tasks[index++] = current.data;
            current = current.next;
        }
        
        return tasks;
    }
    
    public int getSize() {
        return size;
    }
    
    public void bubbleSortByDeadline(boolean ascending) {
        if (size < 2) return;
        
        boolean swapped;
        Node current;
        Node last = null;
        
        do {
            swapped = false;
            current = head;
            
            while (current.next != last) {
                int comparison = current.data.getDeadline().compareTo(current.next.data.getDeadline());
                if ((ascending && comparison > 0) || (!ascending && comparison < 0)) {
                    // Swap data
                    Task temp = current.data;
                    current.data = current.next.data;
                    current.next.data = temp;
                    swapped = true;
                }
                current = current.next;
            }
            last = current;
        } while (swapped);
    }
    
    public void selectionSortByPrioritas(boolean ascending) {
        if (size < 2) return;
        
        Node current = head;
        
        while (current != null) {
            Node min = current;
            Node r = current.next;
            
            while (r != null) {
                if ((ascending && r.data.getPrioritas() > min.data.getPrioritas()) ||
                    (!ascending && r.data.getPrioritas() < min.data.getPrioritas())) {
                    min = r;
                }
                r = r.next;
            }
            
            // Swap data
            Task temp = current.data;
            current.data = min.data;
            min.data = temp;
            
            current = current.next;
        }
    }
    
    // Method untuk debugging
    public void printList() {
        Node current = head;
        System.out.println("Task List:");
        while (current != null) {
            System.out.println("Judul: " + current.data.getJudul() + 
                             ", Deadline: " + current.data.getDeadline() + 
                             ", Prioritas: " + current.data.getPrioritas() + 
                             ", Estimasi: " + current.data.getEstimasiWaktu());
            current = current.next;
        }
        System.out.println("Total tasks: " + size);
    }
}
