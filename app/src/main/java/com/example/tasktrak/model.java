package com.example.tasktrak;

class model {
   String title;
   int mincount;
   int maxcount;

   public model(String title, int mincount,int maxcount) {
      this.title = title;
      this.mincount = mincount;
      this.maxcount=maxcount;
   }

   public String getTitle() {
      return title;
   }

   public int getMincount() {
      return mincount;
   }

   public int getMaxcount() {
      return maxcount;
   }

}
