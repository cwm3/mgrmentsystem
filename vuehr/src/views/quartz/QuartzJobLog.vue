
<template>
    <div>

        <div style="display: flex;justify-content: space-between">
            <div>
                <el-input placeholder="请输入baen名称"
                          clearable
                          @clear="initJobs"
                          style="width: 200px;margin-right: 10px" v-model="searchValue.beanName"
                          @keydown.enter.native="initJobs" :disabled="showAdvanceSearchView"></el-input>
                <el-input placeholder="请输入方法名称"
                          clearable
                          @clear="initJobs"
                          style="width: 200px;margin-right: 10px" v-model="searchValue.methodName"
                          @keydown.enter.native="initJobs" :disabled="showAdvanceSearchView"></el-input>
                <el-select v-model="searchValue.status" filterable placeholder="请选择">
                    <el-option
                            v-for="item in options"
                            :key="item.value"
                            :label="item.label"
                            :value="item.value">
                    </el-option>
                </el-select>
                <el-button icon="el-icon-search" type="primary" style="margin-left: 20px" @click="initJobs" :disabled="showAdvanceSearchView">
                    搜索
                </el-button>
                <!--                    <el-button type="primary" @click="showAdvanceSearchView = !showAdvanceSearchView">-->
                <!--                        <i :class="showAdvanceSearchView?'fa fa-angle-double-up':'fa fa-angle-double-down'"-->
                <!--                           aria-hidden="true"></i>-->
                <!--                        高级搜索-->
                <!--                    </el-button>-->
            </div>
            <div>

                <el-button  type="success" @click="exportData(multipleSelection)" icon="el-icon-download" >
                    导出数据
                </el-button>
<!--                <el-button type="primary" icon="el-icon-plus" @click="AddJob">-->
<!--                    添加任务-->
<!--                </el-button>-->
            </div>
        </div>

        <div style="margin-top: 10px">
            <el-table
                    :data="Jobs"
                    stripe
                    border
                    v-loading="loading"
                    element-loading-text="正在加载..."
                    element-loading-spinner="el-icon-loading"
                    element-loading-background="rgba(0, 0, 0, 0.8)"
                    @selection-change="handleSelectionChange"
                    style="width: 100%">
                <el-table-column
                        type="selection"
                        width="75">
                </el-table-column>
                <el-table-column
                        type="index"
                        fixed
                        align="left"
                        label="序号"
                        width="50">
                </el-table-column>
                <el-table-column
                        prop="jobId"
                        fixed
                        align="left"
                        label="任务ID"
                        width="70">
                </el-table-column>
                <el-table-column
                        prop="beanName"
                        fixed
                        align="left"
                        label="bean名称"
                        width="150">
                </el-table-column>
                <el-table-column
                        prop="methodName"
                        fixed
                        align="left"
                        label="方法名称"
                        width="150">
                </el-table-column>
                <el-table-column
                        prop="params"
                        fixed
                        align="left"
                        label="参数"
                        width="70">
                </el-table-column>
                <el-table-column
                        prop="error"
                        fixed
                        align="left"
                        label="异常信息"
                        >
                </el-table-column>
                <el-table-column
                        prop="times"
                        fixed
                        align="left"
                        label="耗时（毫秒）"
                        width="70">
                </el-table-column>
                <el-table-column
                        prop="createTime"
                        fixed
                        align="left"
                        label="创建时间"
                        width="150">
                    <template  scope="scope">
                        {{timestampToTime(scope.row.createTime)}}
                    </template>
                </el-table-column>
                <el-table-column
                        prop="status"
                        fixed
                        align="left"
                        label="状态"
                        width="80">
                    <template slot-scope="scope">
                        <el-button  v-if="scope.row.status==1"style="padding: 3px" size="small" type="danger">失败</el-button>
                        <el-button  v-if="scope.row.status==0"style="padding: 3px" size="small" type="green">成功</el-button>
                    </template>
                </el-table-column>
                <el-table-column
                        fixed="right"
                        width="170"
                        label="操作">
                    <template slot-scope="scope">
<!--                        <el-button @click="showEditJobView(scope.row)" style="padding: 3px" size="small">编辑</el-button>-->
                        <!--                            <el-button style="padding: 3px" size="mini" type="primary">查看高级资料</el-button>-->
                        <el-button @click="deleteJob(scope.row)" style="padding: 3px" size="small" type="danger">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="display: flex;justify-content: flex-end">
                <el-pagination
                        background
                        @current-change="currentChange"
                        @size-change="sizeChange"
                        layout="sizes, prev, pager, next, jumper, ->, total, slot"
                        :total="total">
                </el-pagination>
            </div>
        </div>

    </div>
</template>


<script>
    import {postRequest} from "../../utils/api";
    import { exportMethod } from '@/utils/util';
    import axios from 'axios'

    export default {
        name: "QuartzJobLog",
        data() {
            return {
                searchValue: {
                    jobId: null,
                    beanName: null,
                    methodName: null,
                    params: null,
                    status: null,
                    error: null,
                    times: null,
                    createTime:null,
                    createTimeFrom: null,
                    createTimeTo: null,
                },
                title: '',
                logId:'',
                ids:[],
                importDataBtnText: '导入数据',
                importDataBtnIcon: 'el-icon-upload2',
                importDataDisabled: false,
                showAdvanceSearchView: false,
                allDeps: [],
                Jobs: [],
                loading: false,
                popVisible: false,
                popVisible2: false,
                dialogVisible: false,
                total: 0,
                page: 1,
                keyword: '',
                keyword1: '',
                size: 10,
                filterText: '',
                // dep: {
                //     name: '',
                //     parentId: -1
                // },
                // pname: '',
                // deps: [],
                //
                // inputDepName: '所属部门',

                defaultProps: {
                    children: 'children',
                    label: 'name'
                },
                multipleSelection: [],
                rules: {

                },
                options: [{
                    value: '1',
                    label: '失败'
                }, {
                    value: '0',
                    label: '成功'
                }]

            }
        },
        mounted() {
            this.initJobs();
            // this.initData();
            // this.initPositions();
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

            handleSelectionChange(val) {
                this.multipleSelection = val;
            },
            onError(err, file, fileList) {
                this.importDataBtnText = '导入数据';
                this.importDataBtnIcon = 'el-icon-upload2';
                this.importDataDisabled = false;
            },
            onSuccess(response, file, fileList) {
                this.importDataBtnText = '导入数据';
                this.importDataBtnIcon = 'el-icon-upload2';
                this.importDataDisabled = false;
                this.initEmps();
            },
            beforeUpload() {
                this.importDataBtnText = '正在导入';
                this.importDataBtnIcon = 'el-icon-loading';
                this.importDataDisabled = true;
            },
            exportData(data) {
                var _this = this;
                data.forEach(element  => {
                    _this.ids.push(element.logId);
                })
                // let  val= [data.id]
                window.open('/jobLog/exportExcel?ids='+ _this.ids, '_parent');
            },
            initDep() {
                this.dep = {
                    name: '',
                    parentId: -1
                }
                this.pname = '';
            },
            sizeChange(currentSize) {
                this.size = currentSize;
                this.initJobs();
            },
            currentChange(currentPage) {
                this.page = currentPage;
                this.initJobs();
            },
            // showAddJobView(data) {
            //
            //     this.inputDepName = data.name;
            //     this.dep.parentId = data.id;
            //     this.dialogVisible = true;
            //     // this.AddJob();
            // },
            showEditJobView(data) {
                this.title = '编辑部门信息';
                console.log(data)
                this.selectPreant(data.parentId);
                this.Jobs = data;
                this.dep.name=data.name;
                this.dep.parentId=data.parentId;
                this.inputDepName = data.name;
                this.dialogVisible = true;
            },
            deleteJob(data) {
                this.$confirm('此操作将永久删除【' + data.beanName + '】, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    console.log(data)
                    this.getRequest("/jobLog/delete/"+data.logId ).then(resp => {
                        if (resp) {
                            this.initJobs();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },


            initJobs(type) {
                this.loading = true;
                let that=this
                let url = 'jobLog/jobLogList'
                let params=''

                     params={
                        beanName: this.searchValue.beanName,
                        methodName: this.searchValue.methodName,
                        status: this.searchValue.status,
                        pageNum:this.page,
                        pageSize:this.size
                    }

                console.log(params)
                this.getRequest(url,params).then(resp => {
                    this.loading = false;
                    if (resp) {
                        this.Jobs = resp.obj.records;
                        this.total = resp.obj.total;
                    }
                });
            },
            selectPreant(val) {
                this.loading = true;
                // let url = '/mgrsystem/basic/department/selectOne'+val ;

                // url += "&name=" + this.keyword;

                this.getRequest("/mgrsystem/basic/department/selectOne" +'?id='+val).then(resp => {
                    this.loading = false;
                    if (resp) {
                        this.pname = resp.data.name;

                    }
                });

            }
        }
    }
</script>

<style>
    /* 可以设置不同的进入和离开动画 */
    /* 设置持续时间和动画函数 */
    .slide-fade-enter-active {
        transition: all .8s ease;
    }

    .slide-fade-leave-active {
        transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
    }

    .slide-fade-enter, .slide-fade-leave-to
        /* .slide-fade-leave-active for below version 2.1.8 */
    {
        transform: translateX(10px);
        opacity: 0;
    }
</style>

