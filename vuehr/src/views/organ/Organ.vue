<template>
    <div >
        <el-input  style=" width:500px ;"
                placeholder="输入关键字进行过滤"
                v-model="filterText">
        </el-input>

        <el-tree
                :data="deps"
                :props="defaultProps"
                :expand-on-click-node="false"
                :filter-node-method="filterNode"
                ref="tree">
            <span class="custom-tree-node" style="display: flex;justify-content: space-between;width: 100%;"
                  slot-scope="{ node, data }">
                    <span>{{data.name }}</span>
            </span>
        </el-tree>
    </div>

</template>




<script>
    export default {
        name: "Organ",
        data() {
            return {
                dialogVisible: false,
                filterText: '',
                dep: {
                    name: '',
                    parentId: -1
                },
                pname: '',
                deps: [],

                defaultProps: {
                    children: 'children',
                    label: 'name'
                }
            }
        },
        watch: {
            filterText(val) {
                this.$refs.tree.filter(val);
            }
        },

        mounted() {
            this.initOrganDeps();
        },
        methods: {
            initOrganDep() {
                this.dep = {
                    name: '',
                    parentId: -1
                }
                this.pname = '';
            },
            addDep2Deps(deps, dep) {
                for (let i = 0; i < deps.length; i++) {
                    let d = deps[i];
                    if (d.id == dep.parentId) {
                        d.children = d.children.concat(dep);
                        if (d.children.length > 0) {
                            d.parent = true;
                        }
                        return;
                    } else {
                        this.addDep2Deps(d.children, dep);
                    }
                }
            },
            doAddDep() {
                this.postRequest("/mgrsystem/basic/department/", this.dep).then(resp => {
                    if (resp) {
                        this.addDep2Deps(this.deps, resp.obj);
                        this.dialogVisible = false;
                        //初始化变量
                        this.initOrganDep();
                    }
                })
            },
            initOrganDeps() {
                this.getRequest("/mgrsystem/basic/department/").then(resp => {
                    if (resp) {
                        this.deps = resp.data;
                        console.log(resp)
                    }
                })
            },
            filterNode(value, data) {
                if (!value) return true;
                return data.name.indexOf(value) !== -1;
            }
        }

    }
</script>
<style>
    .depBtn {
        padding: 2px;
    }
</style>
