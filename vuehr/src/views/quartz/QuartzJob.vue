<template>
    <div>

        <div style="display: flex;justify-content: space-between">
            <div>
                <el-input placeholder="请输入baen名称"
                          clearable
                          @clear="initJobs"
                          style="width: 200px;margin-right: 10px" v-model="searchValue.beanName"
                          @keydown.enter.native="initJobs" :disabled="showAdvanceSearchView"></el-input>
                <el-input placeholder="请输入baen名称"
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
                <el-button icon="el-icon-search" type="primary" style="margin-right: 20px" @click="initJobs" :disabled="showAdvanceSearchView">
                    搜索
                </el-button>
                <!--                    <el-button type="primary" @click="showAdvanceSearchView = !showAdvanceSearchView">-->
                <!--                        <i :class="showAdvanceSearchView?'fa fa-angle-double-up':'fa fa-angle-double-down'"-->
                <!--                           aria-hidden="true"></i>-->
                <!--                        高级搜索-->
                <!--                    </el-button>-->
            </div>
            <div>

                <el-button  type="success" @click="exportData" icon="el-icon-download">
                    导出数据
                </el-button>
                <el-button type="primary" icon="el-icon-plus" @click="AddJob">
                    添加任务
                </el-button>
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
                        width="80">
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
                        width="150">
                </el-table-column>
                <el-table-column
                        prop="cronExpression"
                        fixed
                        align="left"
                        label="Cron表达式"
                        width="150">
                 </el-table-column>
                <el-table-column
                        prop="remark"
                        fixed
                        align="left"
                        label="备注"
                        width="150">
                </el-table-column>
                <el-table-column
                        prop="createTime"
                        fixed
                        align="left"
                        label="创建时间"
                        width="150">
                </el-table-column>

                <el-table-column
                    prop="status"
                    fixed
                    align="left"
                    label="状态"
                    width="150">
                    <template slot-scope="scope">
                        <el-button @click="showEditJobView(scope.row)" style="padding: 3px" size="small">暂停</el-button>
                    </template>
                </el-table-column>
                <el-table-column
                        fixed="right"
                        width="200"
                        label="操作">
                    <template slot-scope="scope">
                        <el-button @click="showEditJobView(scope.row)" style="padding: 3px" size="small">编辑</el-button>
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
        <el-dialog
                :title="title"
                :visible.sync="dialogVisible"
                width="30%">
            <div>
                <el-form :model="dep" :rules="rules" ref="JobFrom">
                    <el-form-item label="上级部门:" prop="preantName">
                        <el-input size="mini" style="width: 150px" prefix-icon="el-icon-edit" v-model="pname"
                        ></el-input>
                    </el-form-item>
                    <el-form-item label="部门名称:" prop="name">
                        <el-input size="mini" style="width: 150px" prefix-icon="el-icon-edit" v-model="dep.name"
                                  placeholder="请输入部门名称"></el-input>
                    </el-form-item>
                </el-form>
            </div>
            <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click=" editJob">确 定</el-button>
  </span>
        </el-dialog>
    </div>
</template>


<script>
    import {postRequest} from "../../utils/api";
    import { exportMethod } from '@/utils/util';
    import axios from 'axios'

    export default {
        name: "QuartzJob",
        data() {
            return {
                searchValue: {
                    beanName: null,
                    methodName: null,
                    status: null,
                    createTime: null,
                    createTimeFrom: null,
                    createTimeTo: null,
                    cronExpression: null,
                    jobId: null,
                    params: null,
                    remark:null
                },
                title: '',
                id:'',
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
                dep: {
                    name: '',
                    parentId: -1
                },
                pname: '',
                deps: [],

                inputDepName: '所属部门',

                defaultProps: {
                    children: 'children',
                    label: 'name'
                },
                rules: {

                },
                options: [{
                    value: '1',
                    label: '正常'
                 }, {
                 value: '0',
                 label: '暂停'
                }]

            }
        },
        mounted() {
            this.initJobs();
            // this.initData();
            // this.initPositions();
        },
        methods: {
            searvhViewHandleNodeClick(data) {
                this.inputDepName = data.name;
                this.searchValue.departmentId = data.id;
                this.popVisible2 = !this.popVisible2
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
                let  val= [data.id]
                window.open('/mgrsystem/basic/department/exportExcel'+ "?ids=" + val, '_parent');
            },
            initDep() {
                this.dep = {
                    name: '',
                    parentId: -1
                }
                this.pname = '';
            },
            showAddJobView(data) {

                this.inputDepName = data.name;
                this.dep.parentId = data.id;
                this.dialogVisible = true;
                // this.AddJob();
            },
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
                this.$confirm('此操作将永久删除【' + data.name + '】, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/mgrsystem/basic/department/" + data.id).then(resp => {
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
            editJob() {
                console.log(this.dep)
                if (this.dep.id) {
                    this.$refs['JobFrom'].validate(valid => {
                        if (valid) {
                            this.postRequest("/mgrsystem/basic/department/upadate", this.Jobs).then(resp => {
                                if (resp) {
                                    this.dialogVisible = false;
                                    this.initJobs();
                                }
                            })
                        }
                    });
                } else {
                    this.$refs['JobFrom'].validate(valid => {
                        if (valid) {
                            this.postRequest("/mgrsystem/basic/department/", this.Jobs).then(resp => {
                                if (resp) {
                                    this.dialogVisible = false;
                                    this.initJobs();
                                }
                            })
                        }
                    });
                }
            },
            // handleNodeClick(data) {
            //     this.inputDepName = data.name;
            //     this.emp.departmentId = data.id;
            //     this.popVisible = !this.popVisible
            // },
            // showDepView() {
            //     this.popVisible = !this.popVisible
            // },
            // showDepView2() {
            //     this.popVisible2 = !this.popVisible2
            // },
            // initPositions() {
            //     this.getRequest('/employee/basic/positions').then(resp => {
            //         if (resp) {
            //             this.positions = resp;
            //         }
            //     })
            // },
            // getMaxWordID() {
            //     this.getRequest("/employee/basic/maxWorkID").then(resp => {
            //         if (resp) {
            //             this.emp.workID = resp.obj;
            //         }
            //     })
            // },
            // initData() {
            //     if (!window.sessionStorage.getItem("nations")) {
            //         this.getRequest('/employee/basic/nations').then(resp => {
            //             if (resp) {
            //                 this.nations = resp;
            //                 window.sessionStorage.setItem("nations", JSON.stringify(resp));
            //             }
            //         })
            //     } else {
            //         this.nations = JSON.parse(window.sessionStorage.getItem("nations"));
            //     }
            //     if (!window.sessionStorage.getItem("joblevels")) {
            //         this.getRequest('/employee/basic/joblevels').then(resp => {
            //             if (resp) {
            //                 this.joblevels = resp;
            //                 window.sessionStorage.setItem("joblevels", JSON.stringify(resp));
            //             }
            //         })
            //     } else {
            //         this.joblevels = JSON.parse(window.sessionStorage.getItem("joblevels"));
            //     }
            //     if (!window.sessionStorage.getItem("politicsstatus")) {
            //         this.getRequest('/employee/basic/politicsstatus').then(resp => {
            //             if (resp) {
            //                 this.politicsstatus = resp;
            //                 window.sessionStorage.setItem("politicsstatus", JSON.stringify(resp));
            //             }
            //         })
            //     } else {
            //         this.politicsstatus = JSON.parse(window.sessionStorage.getItem("politicsstatus"));
            //     }
            //     if (!window.sessionStorage.getItem("deps")) {
            //         this.getRequest('/employee/basic/deps').then(resp => {
            //             if (resp) {
            //                 this.allDeps = resp;
            //                 window.sessionStorage.setItem("deps", JSON.stringify(resp));
            //             }
            //         })
            //     } else {
            //         this.allDeps = JSON.parse(window.sessionStorage.getItem("deps"));
            //     }
            // },
            sizeChange(currentSize) {
                this.size = currentSize;
                this.initJobs();
            },
            currentChange(currentPage) {
                this.page = currentPage;
                this.initJobs();
            },
            // showAddJobView() {
            //     this.emptyEmp();
            //     this.title = '添加部门';
            //     this.getMaxWordID();
            //     this.dialogVisible = true;
            // },
            initJobs(type) {
                this.loading = true;
                let url = 'job/jobList'
                // let params=''
                // if(type){
                //      params={
                //         "beanName": this.searchValue.beanName,
                //         "methodName": this.searchValue.methodName,
                //         "status": this.searchValue.status,
                //         "pageNum":this.page,
                //         "pageSize":this.size
                //     }
                // }
                // console.log(params)
                console.log(this.searchValue)
                this.postRequest(url,this.searchValue).then(resp => {
                    this.loading = false;
                    if (resp) {
                        this.Jobs = resp.data.records;
                        this.total = resp.data.total;
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
