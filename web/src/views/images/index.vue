<template>
  <div class="app-container">
    <div class="search-container">
      <el-button type="primary" size="small" icon="el-icon-upload" />
      <el-button type="primary" size="small" icon="el-icon-sort" />
    </div>
    <el-table
      v-loading="listLoading"
      :data="data.list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
      style="width: 100%"
      @selection-change="handleSelectionChange"
    >
      <el-table-column align="center" :label="$t('imagesManage.imageList.table.number.columnName')" width="50">
        <template slot-scope="scope">
          {{ scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        width="150"
        :label="$t('imagesManage.imageList.table.preview.columnName')"
      >
        <template slot-scope="scope">
          <img class="preview" :src="scope.row.url" alt="">
        </template>
      </el-table-column>
      <el-table-column
        align="left"
        prop="fileName"
        :label="$t('imagesManage.imageList.table.filename.columnName')"
        :show-overflow-tooltip="true"
      />
      <el-table-column
        align="left"
        :label="$t('imagesManage.imageList.table.url.columnName')"
      >
        <template slot-scope="scope">
          <template v-if="scope.row.url">
            <a :href="scope.row.url" target="_blank" style="margin-right: 5px">{{ scope.row.url }}</a>
            <span class="copy-icon" @click="copyUrl(scope.row.url)"><i class="el-icon-document-copy"></i></span>
          </template>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        width="150"
        :label="$t('imagesManage.imageList.table.operate.columnName')"
      >
        <!--<template slot-scope="scope">-->
        <!--<el-button type="danger" :loading="operateLoading" @click="deleteImage()">{{ $t('imagesManage.imageList.delete') }}</el-button>-->
        <!--</template>-->
      </el-table-column>
    </el-table>
    <el-pagination
      class="pagination"
      background
      :current-page="currentPage"
      :page-sizes="[10, 15, 20]"
      :page-size="10"
      layout="total, sizes, prev, pager, next, jumper"
      :total="data.total"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
    />
  </div>
</template>

<script>
import request from '@/utils/request'

export default {
  name: 'List',
  data() {
    return {
      data: {
        list: []
      },
      listLoading: true,
      generateLoading: false,
      operateLoading: false,
      batchOperateLoading: false,
      currentPage: 1,
      pageSize: 10,
      searchForm: {
        searchUsername: '',
        searchEmail: ''
      },
      multipleSelection: []
    }
  },
  mounted() {
    this.fetchData(this.currentPage, this.pageSize)
  },
  methods: {
    fetchData(pageNum, pageSize, searchParams) {
      this.listLoading = true
      request({
        url: '/file/images/list',
        method: 'post',
        params: {
          pageNum: pageNum,
          pageSize: pageSize
        },
        data: searchParams
      }).then(response => {
        console.log(response)
        this.data = response.data
        this.listLoading = false
      })
    },
    deleteImage(id) {
      this.$confirm(this.$t('imagesManage.message.deleteConfirmMessage'), this.$t('imagesManage.message.deleteConfirmMessageTitle'), {
        confirmButtonText: this.$t('imagesManage.message.confirm'),
        cancelButtonText: this.$t('imagesManage.message.cancel'),
        type: 'warning'
      }).then(() => {
        this.operateLoading = true
        request({
          url: '/file/delete',
          method: 'post',
          params: {
            id: id
          }
        }).then((response) => {
          this.operateLoading = false
          this.$message({
            message: response.message,
            type: 'success',
            duration: 3 * 1000,
            showClose: true
          })
          this.fetchData(this.currentPage, this.pageSize)
        })
      })
    },
    copyUrl(data) {
      const url = data
      const oInput = document.createElement('input')
      oInput.value = url
      document.body.appendChild(oInput)
      oInput.select() // 选择对象;

      document.execCommand('Copy') // 执行浏览器复制命令
      this.$message({
        message: this.$t('imagesManage.message.copySuccess'),
        type: 'success',
        duration: 3 * 1000,
        showClose: true
      })
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.fetchData(this.currentPage, val)
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.fetchData(val, this.pageSize)
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    }
  }
}
</script>

<style scoped>
a:hover {
  color: #409EFF;
}

.preview {
  width: 120px;
}

.copy-icon {
  cursor: pointer;
}

.copy-icon :hover {
  color: #409EFF;
}
</style>
