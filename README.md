# 心得反思
這次的旅行日記App作業讓我將課堂上學到的Android技能整合起來，完成一個完整的小型專案。整個專案從畫面設計、Fragment劃分、ViewModel資料共享，到CameraX拍照、Spinner選擇景點、ListView顯示資料，每個部分都需要自己規劃，讓我更清楚理解App的**整體架構以及資料流動**方式。

在介面設計上，我使用ViewPager2結合BottomNavigationView實現四個Fragment的切換，分別是PlaceListFragment、AddPlaceFragment、PhotoDiaryFragment和DiaryListFragment。這樣的設計不僅可以左右滑動切換，也可以點擊底部選單快速切換，讓使用者體驗更加直覺。剛開始規劃時，我擔心多Fragment會讓程式結構混亂，但實作後發現，透過Fragment的分離，每個功能模組都能專注於自身的操作，程式結構反而更清晰，也讓我理解到Android中**畫面分離與模組化設計**的重要性。

在**資料管理**方面，我使用PlaceViewModel與PhotoDiaryViewModel共享資料，並搭配LiveData實現即時更新。當使用者新增景點或照片日記時，PlaceListFragment與DiaryListFragment能立即顯示最新資料，這讓我體會到MVVM設計模式在Android中的優勢：View與資料層分離，UI能隨資料變化自動更新，降低了程式耦合度，也使維護與擴充更方便。

PhotoDiaryFragment是整個App中我最投入的部分。這個Fragment整合CameraX提供的預覽、拍照、停止預覽與儲存功能，並搭配Spinner讓使用者選擇景點。拍照後，圖片會自動與所選景點和當前日期建立關聯，並新增至日記列表。過程中，我特別體會到資料流與UI更新的關聯，例如拍照成功後如何將資料加入ViewModel並通知ListView刷新，這是我過去練習單一功能時很少接觸到的完整流程。

除此之外，我也加入了**影像動畫效果**，像是AlphaAnimation、ScaleAnimation、TranslateAnimation和RotateAnimation，用於PhotoDiaryFragment的ImageView展示。雖然不是App的核心功能，但讓我練習了動畫效果與UI互動，也增強了使用者的操作感。這次練習讓我了解動畫並不只是美化界面，更能增加操作反饋與互動性。

總結來說，這次作業不只是練習技術，更讓我理解到**專案整合與功能設計**的重要性。每個小功能都不是孤立的，如何讓它們在同一個App中流暢運作，是我從這次作業中最大的收穫。我不僅學會將CameraX、Spinner、ViewPager2、BottomNavigationView、LiveData與ViewModel結合使用，也體會到模組化設計、資料共享以及即時UI更新的重要性。透過這個專案，我對**Android開發的全流程**有了更完整的理解，也更有信心面對日後更複雜的App開發挑戰。

<img width="425" height="840" alt="image" src="https://github.com/user-attachments/assets/4c2565a7-d9ba-4b30-be82-2d47e83916af" />
<img width="442" height="828" alt="image" src="https://github.com/user-attachments/assets/880a38d9-068d-47f7-810d-9a49c07858fd" />
<img width="412" height="818" alt="image" src="https://github.com/user-attachments/assets/916d3a10-cc54-4859-bb5f-95ac82e4d528" />
<img width="418" height="786" alt="image" src="https://github.com/user-attachments/assets/0bd6d1ea-da88-4607-9d48-8dfdf7f392a1" />




