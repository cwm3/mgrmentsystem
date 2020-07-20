<template>
    <div class="app-container">
        <div class="filter-container">
            <el-input v-model="keyword" placeholder="请输入关键字" style="width: 220px;" class="filter-item" @keyup.enter.native="getList" />
<!--            <el-select v-model="listQuery.importance" placeholder="" clearable style="width: 90px" class="filter-item">-->
<!--                <el-option v-for="item in importanceOptions" :key="item" :label="item" :value="item" />-->
<!--            </el-select>-->
<!--            <el-select v-model="listQuery.type" placeholder="Type" clearable class="filter-item" style="width: 130px">-->
<!--                <el-option v-for="item in calendarTypeOptions" :key="item.key" :label="item.display_name+'('+item.key+')'" :value="item.key" />-->
<!--            </el-select>-->
<!--            <el-select v-model="listQuery.sort" style="width: 140px" class="filter-item" @change="handleFilter">-->
<!--                <el-option v-for="item in sortOptions" :key="item.key" :label="item.label" :value="item.key" />-->
<!--            </el-select>-->
            <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="getList">
                搜索
            </el-button>
            <el-button v-waves :loading="downloadLoading" class="filter-item" style="margin-right:50px ;float:right"  type="primary" icon="el-icon-download" @click="handleDownload">
                导出
            </el-button>
        </div>
        <div style="margin-top: 10px">

            <el-table
                :key="tableKey"
                v-loading=""
                :data="list"
                border
                fit
                style="width: 100%;"
                @sort-change="sortChange">
            <el-table-column label="序号" prop="id" sortable="custom" align="center" width="70" :class-name="getSortClass('id')">
                <template slot-scope="{row}">
                    <span>{{ row.id }}</span>
                </template>
            </el-table-column>
            <el-table-column label="操作人" width="70px">
                <template slot-scope="{row}">
                    <span>{{ row.username}}</span>

                </template>
            </el-table-column>
            <el-table-column label="操作"  width="120px" align="center">
                <template slot-scope="{row}">
                    <span>{{ row.operation }}</span>
                </template>
            </el-table-column>
<!--            <el-table-column v-if="时间" label="Reviewer" width="110px" align="center">-->
<!--                <template slot-scope="{row}">-->
<!--                    <span >{{ row.time }}</span>-->
<!--                </template>-->
<!--            </el-table-column>-->
            <el-table-column label="方法" >
                <template slot-scope="{row}">
                    <span >{{ row.method}}</span>
                </template>
            </el-table-column>
<!--            <el-table-column label="参数" >-->
<!--                <template slot-scope="{row}">-->
<!--                    <span >{{ row.params }}</span>-->
<!--                </template>-->
<!--            </el-table-column>-->
            <el-table-column label="IP" align="center" width="100px">
                <template slot-scope="{row}">
                    <span>{{ row.ip }}</span>
                </template>
            </el-table-column>
            <el-table-column label="创建时间" width="150px" align="center">
                <template slot-scope="{row}">
                    <span>{{ timestampToTime(row.createTime) }}</span>
                </template>
            </el-table-column>
            <el-table-column label="地址" align="center" width="180px">
                <template slot-scope="{row}">
                    <span >{{ row.location }}</span>
                </template>
            </el-table-column>
                <el-table-column label="操作状态" align="center" width="70px">
                    <template slot-scope="{row}">

                            <el-button  v-if="row.status==0"style="padding: 3px" disabled="true" size="medium" type="danger">失败</el-button>
                            <el-button  v-if="row.status==1 "style="padding: 3px" disabled="true" size="medium" type="success">成功</el-button>

                    </template>
                </el-table-column>
                <el-table-column label="操作类型描述" align="center" width="100px">
                    <template slot-scope="{row}">
                        <span >{{ row.description}}</span>
                    </template>
                </el-table-column>
            <el-table-column label="操作" align="center"width="150px"  class-name="small-padding fixed-width">
                <template slot-scope="{row,$index}">
<!--                    <el-button type="primary" size="small" @click="handleUpdate(row)">-->
<!--                        编辑-->
<!--                    </el-button>-->
                    <el-button  size="mini" type="danger" @click="handleDelete(row)">
                        删除
                    </el-button>
                </template>
            </el-table-column>
        </el-table>

                <el-pagination
                        background
                        @current-change="currentChange"
                        @size-change="sizeChange"
                        layout="sizes, prev, pager, next, jumper, total, slot"
                        :total="total">
                </el-pagination>
        </div>

    </div>
</template>

<script>
    import waves from '@/directive/waves' // waves directive
    // import { parseTime } from '@/utils'
    import Pagination from '@/components/Pagination' // secondary package based on el-pagination





    export default {
        name: 'Syslog',
        components: {Pagination},
        directives: {waves},
        filters: {
            statusFilter(status) {
                const statusMap = {
                    published: 'success',
                    draft: 'info',
                    deleted: 'danger'
                }
                return statusMap[status]
            },
            typeFilter(type) {
                return calendarTypeKeyValue[type]
            }
        },
        data() {
            return {
                tableKey: 0,
                list: null,
                total: 0,
                page: 1,
                size: 10,
                keyword: '',
                listLoading: true,
                listQuery: {
                    page: 1,
                    limit: 10,
                    importance: undefined,
                    keyword: undefined,
                    type: undefined,
                    sort: '+id'
                },
                importanceOptions: [1, 2, 3],
                sortOptions: [{label: 'ID Ascending', key: '+id'}, {label: 'ID Descending', key: '-id'}],
                statusOptions: ['published', 'draft', 'deleted'],
                showReviewer: false,
                temp: {
                    id: undefined,
                    username: '',
                    operation: '',
                    time: 1,
                    method: '',
                    params: '',
                    ip: '',
                    location: '',
                    status:1,
                    type:1,
                    description:'',
                    createTime: new Date(),

                },
                dialogFormVisible: false,
                dialogStatus: '',
                textMap: {
                    update: 'Edit',
                    create: 'Create'
                },
                dialogPvVisible: false,
                pvData: [],
                rules: {
                    type: [{required: true, message: 'type is required', trigger: 'change'}],
                    timestamp: [{type: 'date', required: true, message: 'timestamp is required', trigger: 'change'}],
                    title: [{required: true, message: 'title is required', trigger: 'blur'}]
                },
                downloadLoading: false
            }
        },
        created() {
            this.getList()
        },
        methods: {
            timestampToTime(timestamp) {
                var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
                var Y = date.getFullYear() + '-';
                var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
                var D = (date.getDate() < 10 ? '0'+date.getDate() : date.getDate()) + ' ';
                var h = (date.getHours() < 10 ? '0'+date.getHours() : date.getHours()) + ':';
                var m = (date.getMinutes() < 10 ? '0'+date.getMinutes() : date.getMinutes()) + ':';
                var s = (date.getSeconds() < 10 ? '0'+date.getSeconds() : date.getSeconds());
                return Y+M+D+h+m+s;
                // return Y+M+D;
            },
            getList(type) {
                let url = '/mgrsystem/syslog/?pageNum=' + this.page + '&pageSize=' + this.size;
                if (type) {
                    url += "&name=" + this.keyword;
                }

                this.listLoading = true
                this.getRequest(url).then(resp => {
                    this.loading = false;
                    if (resp) {
                        this.list = resp.obj.records;
                        this.total = resp.obj.total;
                    }
                })
            },
            handleFilter() {
                this.getList()
            },

                sortChange(data) {
                    const { prop, order } = data
                    if (prop === 'id') {
                        this.sortByID(order)
                    }
                },
            sizeChange(currentSize) {
                this.size = currentSize;
                this.getList();
            },
            currentChange(currentPage) {
                this.page = currentPage;
                this.getList();
            },
                sortByID(order) {
                    if (order === 'ascending') {
                        this.listQuery.sort = '+id'
                    } else {
                        this.listQuery.sort = '-id'
                    }
                    this.handleFilter()
                },
                resetTemp() {
                    this.temp = {
                        id: undefined,
                        importance: 1,
                        remark: '',
                        timestamp: new Date(),
                        title: '',
                        status: 'published',
                        type: ''
                    }
                },
            handleDownload() {
                this.downloadLoading = true
                let  fileName = '操作日志表'
                // const filterVal = ['序号', '操作人', '操作', '耗时（毫秒）','方法', '参数', 'ip', '创建时间', '地址']
                window.open('/mgrsystem/syslog/export'+ "?headers= " + filterVal + "&fileName="+ fileName, '_parent');
                this.downloadLoading = false
            },
                handleCreate() {
                    this.resetTemp()
                    this.dialogStatus = 'create'
                    this.dialogFormVisible = true
                    this.$nextTick(() => {
                        this.$refs['dataForm'].clearValidate()
                    })
                },
                createData() {
                    this.$refs['dataForm'].validate((valid) => {
                        if (valid) {
                            this.temp.id = parseInt(Math.random() * 100) + 1024 // mock a id
                            this.temp.author = 'vue-element-admin'
                            createArticle(this.temp).then(() => {
                                this.list.unshift(this.temp)
                                this.dialogFormVisible = false
                                this.$notify({
                                    title: 'Success',
                                    message: 'Created Successfully',
                                    type: 'success',
                                    duration: 2000
                                })
                            })
                        }
                    })
                },
                handleUpdate(row) {
                    this.temp = Object.assign({}, row) // copy obj
                    this.temp.timestamp = new Date(this.temp.timestamp)
                    this.dialogStatus = 'update'
                    this.dialogFormVisible = true
                    this.$nextTick(() => {
                        this.$refs['dataForm'].clearValidate()
                    })
                },

                handleDelete(data) {
                   let _that = this
                    this.$confirm('此操作将永久删除【' + data.id + '】, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        this.getRequest("/mgrsystem/syslog/delete?id=" + data.id).then(resp => {
                            if (resp) {
                                // _that.$message.success('删除成功');

                                // Vue.prototype.$message({
                                //     type: 'success',
                                //     message: '删除成功',
                                // });
                                this.getList();
                            }
                        })
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消删除'
                        });
                    });

                },
                handleFetchPv(pv) {
                    fetchPv(pv).then(response => {
                        this.pvData = response.data.pvData
                        this.dialogPvVisible = true
                    })
                },
                formatJson(filterVal) {
                    return this.list.map(v => filterVal.map(j => {
                        if (j === 'timestamp') {
                            return parseTime(v[j])
                        } else {
                            return v[j]
                        }
                    }))
                },
                getSortClass: function(key) {
                    const sort = this.listQuery.sort
                    return sort === `+${key}` ? 'ascending' : 'descending'
                }

        }
    }
</script>
