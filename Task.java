package PengaturJadwal;

public class Task {
    private String judul;
    private String deadline;
    private int prioritas; // 1=rendah, 2=sedang, 3=tinggi
    private int estimasiWaktu; // dalam jam
    
    public Task(String judul, String deadline, int prioritas, int estimasiWaktu) {
        this.judul = judul;
        this.deadline = deadline;
        this.prioritas = prioritas;
        this.estimasiWaktu = estimasiWaktu;
    }
    
    // Getters
    public String getJudul() { 
        return judul; 
    }
    public String getDeadline() { 
        return deadline; 
    }
    public int getPrioritas() { 
        return prioritas; 
    }
    public int getEstimasiWaktu() { 
        return estimasiWaktu; 
    }
    
    // Setters
    public void setJudul(String judul) { 
        this.judul = judul; 
    }
    public void setDeadline(String deadline) { 
        this.deadline = deadline; 
    }
    public void setPrioritas(int prioritas) { 
        this.prioritas = prioritas; 
    }
    public void setEstimasiWaktu(int estimasiWaktu) { 
        this.estimasiWaktu = estimasiWaktu; 
    }
}
