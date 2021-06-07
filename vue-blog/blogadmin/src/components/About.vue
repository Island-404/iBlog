<template>
  <el-container v-loading="loading" class="post-about">
    
    <el-main class="main">
      <div id="editor">
        <mavon-editor style="height: 100%;width: 100%;" ref=md @imgAdd="imgAdd"
                      @imgDel="imgDel" v-model="mdContent"></mavon-editor>
      </div>
      <div style="display: flex;align-items: center;margin-top: 15px;justify-content: flex-end">
        <el-button @click="cancelEdit">放弃修改</el-button>
        
        <el-button type="primary" @click="saveBlog(1)">发表</el-button>
        
      </div>
    </el-main>
  </el-container>
</template>
<script>
  import {postRequest} from '../utils/api'
  import {putRequest} from '../utils/api'
  import {deleteRequest} from '../utils/api'
  import {getRequest} from '../utils/api'
  import {uploadFileRequest} from '../utils/api'
  // Local Registration
  import {mavonEditor} from 'mavon-editor'
  // 可以通过 mavonEditor.markdownIt 获取解析器markdown-it对象
  import 'mavon-editor/dist/css/index.css'
  import {isNotNullORBlank} from '../utils/utils'

  export default {
    mounted: function () {
      var _this = this;
      this.loading = true;
      getRequest("/api/about").then(resp=> {
        _this.loading = false;
        if (resp.status == 200) {
          _this.mdContent = resp.data.data;
        } else {
          _this.$message({type: 'error', message: '页面加载失败!'})
        }
      }, resp=> {
        _this.loading = false;
        _this.$message({type: 'error', message: '页面加载失败!'})
      })
      
    },
    components: {
      mavonEditor
    },
    methods: {
      cancelEdit(){
        this.$router.go(-1)
      },
      saveBlog(state){
        if (!( this.mdContent)) {
          this.$message({type: 'error', message: '数据不能为空!'});
          return;
        }
        var _this = this;
        _this.loading = true;
        
        putRequest("/api/admin/about", {
          mdContent: _this.mdContent
          }).then(resp=> {
          _this.loading = false;
          if (resp.status == 200 && resp.data.code == 200) {
            //_this.mdContent = resp.data.data;
            _this.$message({type: 'success', message:'发布成功'});
//            if (_this.from != undefined) {
            window.bus.$emit('blogTableReload')
          }
        }, resp=> {
          _this.loading = false;
          _this.$message({type: 'error', message: '发布失败'});
        })
      },
      imgAdd(pos, $file){
        var _this = this;
        // 第一步.将图片上传到服务器.
        var formdata = new FormData();
        formdata.append('image', $file);
        uploadFileRequest("/api/uploadimg", formdata).then(resp=> {
          var json = resp.data;
          if (json.msg == 'success') {
//            _this.$refs.md.$imgUpdateByUrl(pos, json.msg)
            _this.$refs.md.$imglst2Url([[pos, json.data]])
          } else {
            _this.$message({type: json.msg, message: json.data});
          }
        });
      },
      imgDel(pos){

      }
      // showInput() {
      //   this.tagInputVisible = true;
      //   this.$nextTick(_ => {
      //     this.$refs.saveTagInput.$refs.input.focus();
      //   });
      // }
    },
    data() {
      return {
        loading: false,
        from: '',
        
        mdContent: '',
        
      }
    }
  }
</script>
<style>
  .post-about > .main > #editor {
    width: 100%;
    height: 450px;
    margin-top: 10px;
    text-align: left;
  }

  .post-about > .main {
    /*justify-content: flex-start;*/
    display: flex;

    flex-direction: column;
    padding-left: 5px;
    background-color: #ececec;
    padding-top: 0px;
  }

  .post-about {
  }
</style>
