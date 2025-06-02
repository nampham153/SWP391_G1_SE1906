# Project SWP391 của nhóm 1
## 1. Cách lấy project và build project
- Đầu tiên, tạo 1 project mới trong NetBeans, có thể đặt bất kì tên gì cho project, khuyến khích nên đặt tên theo branch mình đang làm:
![Create a new NetBeans project](/assets/create_project.png)
- Tiếp theo, di chuyển đến thư mục project vừa tạo, chọn open in terminal:
![Open a terminal inside the project folder](/assets/open_in_terminal.png)
- Tạo Git Repository config local repo và pull code trên master branch với các câu lệnh sau:
```
git init
git config user.name "<Tên người dùng trên github repo>"
git config user.email "<Email người dùng trên github repo>"
git config init.defaultBranch "master"
git remote add origin https://github.com/nampham153/SWP391_G1_SE1906.git
git pull origin master
```
![Config git](/assets/config_git.png)
- Nếu pull mà trên terminal yêu cầu xoá file gì thì xoá file đấy, rồi pull lại master
- Chuyển sang nhánh của mình hoặc nhánh khác bằng câu lệnh
```
git checkout <Tên nhánh của mình>
```
- Trường hợp chưa có nhánh hoặc đã pull code mà checkout không thấy nhánh thì dùng câu lệnh này để tạo nhánh và chuyển sang nhánh vừa tạo:
```
git checkout -b <Tên nhánh của mình trên github>
```
- Để xem các nhánh đang có trên local và nhánh mình pull trên remote, dùng lệnh:
```
git branch -a
```
![Branch config](/assets/branch_config.png)
- Lúc này trong project sẽ có cả source code, thư viện và UI, vào NetBeans và add tất cả các thư viện trong thư mục lib vừa pull về (Libraries --> Add JAR/folder):
![Add libraries](/assets/add_libraries.png)
- Clean and build project trong NetBeans
## 2. Cách push code lên GitHub
- Nếu chưa chuyển sang branch của mình thì dùng lệnh checkout branch để chuyển sang branch của mình, hạn chế push lên master để tránh source code chung bị lỗi
- Sử dụng các lệnh này để push lên branch của mình:
```
git add .
git commit -m "<Mô tả commit>"
git push origin "<Nhánh của mình>"
```
- Tạo pull request trên GitHub, compare master với branch của mình, nếu không có lỗi thì tạo pull request và tự merge vào master, nếu có lỗi thì pull code về sửa và làm lại:
![Pull request](/assets/pull_request.png)
## 3. Kết nối đến database dùng chung (WIP)
