<template>
    <div>

            <div style="display: flex;justify-content: space-between">
                <div>
                    <el-input placeholder="请输入部门名进行搜索，可以直接回车搜索..." prefix-icon="el-icon-search"
                              clearable
                              @clear="initDepts"
                              style="width: 350px;margin-right: 10px" v-model="keyword"
                              @keydown.enter.native="initDepts" :disabled="showAdvanceSearchView"></el-input>
                    <el-button icon="el-icon-search" type="primary" @click="initDepts" :disabled="showAdvanceSearchView">
                        搜索
                    </el-button>
<!--                    <el-button type="primary" @click="showAdvanceSearchView = !showAdvanceSearchView">-->
<!--                        <i :class="showAdvanceSearchView?'fa fa-angle-double-up':'fa fa-angle-double-down'"-->
<!--                           aria-hidden="true"></i>-->
<!--                        高级搜索-->
<!--                    </el-button>-->
                </div>
                <div>
                    <el-upload
                            :show-file-list="false"
                            :before-upload="beforeUpload"
                            :on-success="onSuccess"
                            :on-error="onError"
                            :disabled="importDataDisabled"
                            style="display: inline-flex;margin-right: 8px"
                            action="/mgrsystem/basic/department/importExcel">
                        <el-button :disabled="importDataDisabled" type="success" :icon="importDataBtnIcon">
                            {{importDataBtnText}}
                        </el-button>
                    </el-upload>
                    <el-button  type="success" @click="exportData" icon="el-icon-download">
                        导出数据
                    </el-button>
                    <el-button type="primary" icon="el-icon-plus" @click="AddDept">
                        添加用户
                    </el-button>
                </div>
            </div>

            <div style="margin-top: 10px">
                <el-table
                        :data="depts"
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
                            prop="name"
                            fixed
                            align="left"
                            label="部门名称"
                            >
                    </el-table-column>
                    <el-table-column
                            fixed="right"
                            width="200"
                            label="操作">
                        <template slot-scope="scope">
                            <el-button @click="showEditDeptView(scope.row)" style="padding: 3px" size="small">编辑</el-button>
<!--                            <el-button style="padding: 3px" size="mini" type="primary">查看高级资料</el-button>-->
                            <el-button @click="deleteDept(scope.row)" style="padding: 3px" size="small" type="danger">删除
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
                <el-form :model="dep" :rules="rules" ref="deptFrom">
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
    <el-button type="primary" @click=" editDept">确 定</el-button>
  </span>
        </el-dialog>
    </div>
</template>


<script>
    import {postRequest} from "../../utils/api";
    import { exportMethod } from '@/utils/util';
    import axios from 'axios'

    export default {
        name: "OrganDept",
        data() {
            return {
                // searchValue: {
                //     politicId: null,
                //     nationId: null,
                //     jobLevelId: null,
                //     posId: null,
                //     engageForm: null,
                //     departmentId: null,
                //     beginDateScope: null
                // },
                title: '',
                id:'',
                importDataBtnText: '导入数据',
                importDataBtnIcon: 'el-icon-upload2',
                importDataDisabled: false,
                showAdvanceSearchView: false,
                allDeps: [],
                depts: [],
                loading: false,
                popVisible: false,
                popVisible2: false,
                dialogVisible: false,
                total: 0,
                page: 1,
                keyword: '',
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

                }
            }
        },
        mounted() {
            this.initDepts();
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
            showAddDeptView(data) {

                this.inputDepName = data.name;
                this.dep.parentId = data.id;
                this.dialogVisible = true;
                // this.AddDept();
            },
            showEditDeptView(data) {
                this.title = '编辑部门信息';
                console.log(data)
                this.selectPreant(data.parentId);
                this.depts = data;
                this.dep.name=data.name;
                this.dep.parentId=data.parentId;
                this.inputDepName = data.name;
                this.dialogVisible = true;
            },
            deleteDept(data) {
                this.$confirm('此操作将永久删除【' + data.name + '】, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/mgrsystem/basic/department/" + data.id).then(resp => {
                        if (resp) {
                            this.initDepts();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            editDept() {
                console.log(this.dep)
                if (this.dep.id) {
                    this.$refs['deptFrom'].validate(valid => {
                        if (valid) {
                            this.postRequest("/mgrsystem/basic/department/upadate", this.depts).then(resp => {
                                if (resp) {
                                    this.dialogVisible = false;
                                    this.initDepts();
                                }
                            })
                        }
                    });
                } else {
                    this.$refs['deptFrom'].validate(valid => {
                        if (valid) {
                            this.postRequest("/mgrsystem/basic/department/", this.depts).then(resp => {
                                if (resp) {
                                    this.dialogVisible = false;
                                    this.initDepts();
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
                this.initDepts();
            },
            currentChange(currentPage) {
                this.page = currentPage;
                this.initDepts();
            },
            // showAddDeptView() {
            //     this.emptyEmp();
            //     this.title = '添加部门';
            //     this.getMaxWordID();
            //     this.dialogVisible = true;
            // },
            initDepts(type) {
                this.loading = true;
                let url = '/mgrsystem/basic/department/pageList' + '?pageNum='+ this.page + '&pageSize=' + this.size;

                    url += "&name=" + this.keyword;

                this.getRequest(url).then(resp => {
                    this.loading = false;
                    if (resp) {
                        this.depts = resp.data.records;
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